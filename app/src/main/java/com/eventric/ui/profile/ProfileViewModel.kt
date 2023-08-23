package com.eventric.ui.profile

import androidx.lifecycle.ViewModel
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
) : ViewModel() {

    private val userIdFlow = MutableStateFlow("")
    val userFlow = userIdFlow.flatMapLatest { id ->
        if (id != "")
            userRepository.getUser(id).map { it.second }
        else
            userRepository.getUser(loggedUserFlow.first().first).map { it.second }
    }

    val loggedUserFlow = userRepository.user
    fun setUserId(id: String) {
        userIdFlow.value = id
    }

    val isUserSelfFlow = combine(
        userFlow,
        loggedUserFlow
    ) { (id, _), (userId, _) ->
        id == userId
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
            .map { Pair(it.first, it.second) }
    }

    val followedFlow = combine(
        userRepository.getAllUsers(),
        userFlow
    ) { users, user ->
        val followingUserIds = user.followingUsers
        users
            .filter { followingUserIds.contains(it.first) }
            .map { (userId, user) ->
                Pair(userId, user)
            }
    }

    suspend fun changeUserFollow(
        isFollowed: Boolean,
    ) = userRepository.addOrRemoveFollow(
        followedUserId = userIdFlow.first(),
        followingUserId = loggedUserFlow.first().first,
        addOrRemove = isFollowed
    )

    fun logout(){
        userRepository.logout()
    }
}