package com.eventric.ui.dispatcher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.eventric.repo.UserRepository
import com.eventric.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class DispatcherViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
) : ViewModel() {

    companion object {
        const val KEY_SHOW_SPLASH = "show_splash"
    }

    private val splashShown = savedStateHandle.getStateFlow(KEY_SHOW_SPLASH, false)
    private val user = userRepository.user

    val dispatcherStateFlow: Flow<DispatcherState> = combine(
        user,
        splashShown,
    ) { user, splashShown ->
        if (splashShown) {
            // configuration is loaded, lets check for user status
            if (user == User.EMPTY_USER) {
                DispatcherState.USER_NOT_LOGGED
            } else {
                DispatcherState.USER_LOGGED
            }
        } else {
            DispatcherState.SPLASH
        }
    }

    fun setSplashShown() {
        savedStateHandle[KEY_SHOW_SPLASH] = true
    }
}

enum class DispatcherState {
    LOADING,
    SPLASH,
    CONFIG_LOAD_FAILED,
    USER_NOT_LOGGED,
    USER_LOGGED,
}