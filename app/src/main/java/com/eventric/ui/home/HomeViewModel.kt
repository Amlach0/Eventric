package com.eventric.ui.home

import androidx.lifecycle.ViewModel
import com.eventric.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    val loggedUserFlow = userRepository.user

    val notificationsPresents = loggedUserFlow.map { it.second.notifications.isNotEmpty() }
}