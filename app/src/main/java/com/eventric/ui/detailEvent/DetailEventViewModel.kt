package com.eventric.ui.detailEvent

import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.UserRepository
import com.eventric.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DetailEventViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val eventIdFlow = MutableStateFlow("")
    val eventFlow = eventIdFlow.flatMapLatest { id ->
        if (id != "")
            eventRepository.getEvent(id).map { it.second ?: Event() }
        else
            flowOf()
    }
    val organizerFlow = eventFlow.flatMapLatest { event ->
        userRepository.getUser(event.organizer ?: "")
    }

    private val loggedUserFlow = userRepository.user

    fun setEventId(id: String) {
        eventIdFlow.value = id
    }

    val isFavoriteFlow = combine(
        eventIdFlow,
        loggedUserFlow
    ) { eventId, (_, user) ->
        user.favoriteEvents.contains(eventId)
    }

    val isSubscribedFlow = combine(
        eventFlow,
        loggedUserFlow
    ) { event, (userId, _) ->
        event.subscribed.contains(userId)
    }

    val isOrganizerFlow = combine(
        eventFlow,
        loggedUserFlow
    ) { event, (userId, _) ->
        event.organizer == userId
    }

    val isOrganizerFollowedFlow = combine(
        organizerFlow,
        loggedUserFlow
    ) { (idOrganizer, _), (_, loggedUser) ->
        loggedUser.followingUsers.contains(idOrganizer)
    }

    val subscribedUsersFlow = combine(
        eventFlow,
        userRepository.getAllUsers()
    ) { event, users ->
        val subscribedUserIds = event.subscribed
        users
            .filter { subscribedUserIds.contains(it.first) }
            .map { Triple(it.first, true, it.second) }
    }

    val invitableUsersFlow = combine(
        eventIdFlow,
        eventFlow,
        userRepository.getAllUsers(),
        loggedUserFlow,
        organizerFlow
    ) { eventId, event, users, (loggedUserId, loggedUser), (organizerId, _) ->
        val subscribedUserIds = event.subscribed
        val followingUserIds = loggedUser.followingUsers
        users
            .filter { !subscribedUserIds.contains(it.first) && followingUserIds.contains(it.first) && organizerId != it.first }
            .map { (userId, user) ->
                val notifications = user.notifications
                Triple(
                    userId,
                    !notifications.none { it.userId == loggedUserId && it.eventId == eventId },
                    user
                )
            }
    }


    suspend fun changeFavourite(
        isFavourite: Boolean,
    ) = userRepository.addOrRemoveFavorite(
        userId = loggedUserFlow.first().first,
        eventId = eventIdFlow.value,
        addOrRemove = isFavourite
    )

    suspend fun changeSubscribe(
        isSubscribed: Boolean,
    ) = eventRepository.addOrRemoveSubscribe(
        eventId = eventIdFlow.value,
        userId = loggedUserFlow.first().first,
        addOrRemove = isSubscribed
    )

    suspend fun changeOrganizerFollow(
        isFollowed: Boolean,
    ) = userRepository.addOrRemoveFollow(
        followedUserId = organizerFlow.first().first,
        followingUserId = loggedUserFlow.first().first,
        addOrRemove = isFollowed
    )

    suspend fun changeUserInvite(
        userId: String,
        isInvited: Boolean
    ) = userRepository.addOrRemoveInvite(
        userId = loggedUserFlow.first().first,
        invitedUserId = userId,
        eventId = eventIdFlow.value,
        addOrRemove = isInvited
    )
}

