package com.eventric.ui.profile

import android.net.Uri
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
    eventRepository: EventRepository,
    private val imagesRepository: ImagesRepository,
) : ViewModel() {

    private val userIdFlow = MutableStateFlow("")
    private val userPairFlow = userIdFlow.flatMapLatest { id ->
        if (id != "")
            userRepository.getUser(id)
        else
            loggedUserFlow
    }
    val userFlow = userPairFlow.map { it.second }

    private val loggedUserFlow = userRepository.user
    fun setUserId(id: String) {
        userIdFlow.value = id
    }

    val uriImageFlow = userPairFlow.flatMapLatest { (userId) ->
        imagesRepository.downloadUserImage(userId)
    }

    val isUserFollowedFlow = combine(
        userPairFlow,
        loggedUserFlow
    ) { (id, _), (_, loggedUser) ->
        loggedUser.followingUsers.contains(id)
    }

    val followersFlow = combine(
        userRepository.getAllUsers(),
        userPairFlow,
        imagesRepository.downloadAllUserImages()
    ) { users, (userId), images ->
        users
            .filter { it.second.followingUsers.contains(userId) }
            .map { (userId, user) ->
                Triple(userId, user, images[userId] ?: Uri.EMPTY)
            }
    }

    val followedFlow = combine(
        userRepository.getAllUsers(),
        userPairFlow,
        imagesRepository.downloadAllUserImages()
    ) { users, (_, user), images ->
        val followingUserIds = user.followingUsers
        users
            .filter { followingUserIds.contains(it.first) }
            .map { (userId, user) ->
                Triple(userId, user, images[userId] ?: Uri.EMPTY)
            }
    }

    val loggedUserId = loggedUserFlow.map { it.first }

    val organizedEvents = combine(
        eventRepository.getAllEvents(),
        userPairFlow,
        userRepository.user,
        imagesRepository.downloadAllEventsImages()
    ) { events, (userId), (_, loggedUser), images ->
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
                    images[id] ?: Uri.EMPTY
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