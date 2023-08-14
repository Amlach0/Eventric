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
            eventRepository.getEvent(id).map { it.second ?: Event()}
        else
            flowOf()
    }
    val organizerFlow = eventFlow.flatMapLatest { event ->
        userRepository.getUser(event.organizer ?: "")
    }

    val loggedUserFlow = userRepository.user

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


    suspend fun changeFavourite(
        isFavourite: Boolean,
    ) {
        val userId = loggedUserFlow.first().first
        val eventId = eventIdFlow.value
        val favorites = loggedUserFlow.first().second.favoriteEvents.toMutableList()

        val isFavouriteNow = favorites.contains(eventId)

        if (isFavourite != isFavouriteNow) {
            if (isFavourite) favorites.add(eventId) else favorites.remove(eventId)

            userRepository.updateUser(
                userId = userId,
                mapFieldValue = mapOf("favoriteEvents" to favorites.toList())
            )
        }

    }

    suspend fun changeSubscribe(
        isSubscribed: Boolean,
    ) {
        val userId = loggedUserFlow.first().first
        val eventId = eventIdFlow.value
        val subscribers = eventFlow.first().subscribed.toMutableList()

        val isSubscribedNow = subscribers.contains(userId)

        if (isSubscribed != isSubscribedNow) {
            if (isSubscribed) subscribers.add(userId) else subscribers.remove(userId)

            eventRepository.updateEvent(
                eventId = eventId,
                mapFieldValue = mapOf("subscribed" to (subscribers.toList()))
            )
        }
    }

    suspend fun changeFollow(
        isFollowed: Boolean,
    ) {
        val loggedUserId = loggedUserFlow.first().first
        val organizerId = organizerFlow.first().first
        val following = loggedUserFlow.first().second.followingUsers.toMutableList()

        val isFollowedNow = following.contains(organizerId)

        if (isFollowed != isFollowedNow) {
            if (isFollowed) following.add(organizerId) else following.remove(organizerId)

            userRepository.updateUser(
                userId = loggedUserId,
                mapFieldValue = mapOf("followingUsers" to following.toList())
            )
        }
    }
}

