package com.eventric.ui.profile

import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.UserRepository
import com.eventric.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val userIdFlow = MutableStateFlow("")
    val userFlow = userIdFlow.flatMapLatest { id ->
        if (id != "")
            userRepository.getUser(id).map { it.second ?: User() }
        else
            flowOf()
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

    /*TODO
    val HowManyFollowersFlow = combine(
        userFlow,
        userRepository.getAllUsers()
    ) { (id, _), (_, loggedUser) ->
        loggedUser.followingUsers.contains(id)
    }
    */
    suspend fun changeUserFollow(
        isFollowed: Boolean,
    ) = userRepository.addOrRemoveFollow(
        followedUserId = userIdFlow.first(),
        followingUserId = loggedUserFlow.first().first,
        addOrRemove = isFollowed
    )
}