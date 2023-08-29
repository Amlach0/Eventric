package com.eventric.ui.profile

import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.ImagesRepository
import com.eventric.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    private val userIdFlow = MutableStateFlow("")
    val userFlow = userIdFlow.flatMapLatest { id ->
        if (id != "")
            userRepository.getUser(id).map { it.second }
        else
            loggedUserFlow.map { it.second }
    }

    private val loggedUserFlow = userRepository.user
    fun setUserId(id: String) {
        userIdFlow.value = id
    }

    val uriImageFlow = combine(
        userIdFlow,
        loggedUserFlow
    ) { userId, (loggedUserId, _) ->
        Pair(userId, loggedUserId)
    }.flatMapLatest {(userId, loggedUserId) ->
        imagesRepository.downloadUserImage(if (userId != "") userId else loggedUserId)
    }

    val isUserFollowedFlow = combine(
        userFlow,
        loggedUserFlow
    ) { (id, _), (_, loggedUser) ->
        loggedUser.followingUsers.contains(id)
    }

    val followersFlow = combine(
        userRepository.getAllUsers(),
        userIdFlow
    ) { users, userId ->
        users
            .filter { it.second.followingUsers.contains(userId) }
            .map { (userId, user) ->
                Triple(userId, user, imagesRepository.downloadUserImage(userId).first())
            }
    }

    val followedFlow = combine(
        userRepository.getAllUsers(),
        userFlow
    ) { users, user ->
        val followingUserIds = user.followingUsers
        users
            .filter { followingUserIds.contains(it.first) }
            .map { (userId, user) ->
                Triple(userId, user, imagesRepository.downloadUserImage(userId).first())
            }
    }

    val loggedUserId = loggedUserFlow.map { it.first }

    val organizedEvents = combine(
        eventRepository.getAllEvents(),
        userIdFlow,
        userFlow,
        userRepository.user,
    ) { events, userId, _, (_, loggedUser) ->
        events
            .filter { (_, event) ->
                event.organizer == userId
                        && (event.type == "public"
                        || (event.type == "private"
                        && loggedUser.followingUsers.contains(event.organizer)))
            }
            .map { (id, event) ->
                Triple(
                    Pair(
                        id,
                        event
                    ),
                    loggedUser.favoriteEvents.contains(id),
                    imagesRepository.downloadEventImage(id).first()
                )
            }
    }

    suspend fun changeUserFollow(
        isFollowed: Boolean,
    ) = userRepository.addOrRemoveFollow(
        followedUserId = userIdFlow.first(),
        followingUserId = loggedUserFlow.first().first,
        addOrRemove = isFollowed
    )

    fun logout() {
        userRepository.logout()
    }

}