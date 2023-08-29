package com.eventric.ui.notifications

import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.UserRepository
import com.eventric.vo.Event
import com.eventric.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val loggedUserFlow = userRepository.user

    val notificationsFlow = combine(
        loggedUserFlow,
        userRepository.getAllUsers(),
        eventRepository.getAllEvents()
    ) { (_, loggedUser), users, events ->
        loggedUser.notifications.map { notification ->
            Triple(
                users.findLast { it.first == notification.userId } ?: Pair("", User()),
                Pair(notification.text ?: "", notification.time ?: ""),
                events.findLast { it.first == notification.eventId } ?: Pair("", Event())
            )
        }
    }

    suspend fun acceptUserInvite(
        userId: String,
        eventId: String,
    ) {
        eventRepository.addOrRemoveSubscribe(
            eventId = eventId,
            userId = loggedUserFlow.first().first,
            addOrRemove = true
        )
        userRepository.addOrRemoveInvite(
            userId = userId,
            invitedUserId = loggedUserFlow.first().first,
            eventId = eventId,
            addOrRemove = false
        )
    }

    suspend fun rejectUserInvite(
        userId: String,
        eventId: String,
    ) = userRepository.addOrRemoveInvite(
        userId = userId,
        invitedUserId = loggedUserFlow.first().first,
        eventId = eventId,
        addOrRemove = false
    )

    suspend fun clearAllNotifications() =
        userRepository.clearAllNotifications(loggedUserFlow.first().first)
}