package com.eventric.ui.dispatcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.eventric.ui.theme.EventricTheme
import kotlinx.coroutines.delay

const val SPLASH_TIME_MILLIS = 2000L

@Composable
fun DispatcherScreen(
    dispatcherViewModel: DispatcherViewModel = hiltViewModel(),
    goToHome: () -> Unit,
    goToLogin: () -> Unit
) {

    val dispatcherState by dispatcherViewModel.dispatcherStateFlow.collectAsState(DispatcherState.LOADING)

    LaunchedEffect(dispatcherState) {
        when (dispatcherState) {
            DispatcherState.LOADING -> {
                // waiting for another state to come
            }
            DispatcherState.SPLASH -> {
                delay(SPLASH_TIME_MILLIS)
                dispatcherViewModel.setSplashShown()
            }
            DispatcherState.CONFIG_LOAD_FAILED -> {
                // stay in page showing alert and retry button
            }
            DispatcherState.USER_LOGGED -> {
                goToHome()
            }
            DispatcherState.USER_NOT_LOGGED -> {
                goToLogin()
            }
        }
    }

    EventricTheme {
        DispatcherContent(dispatcherState = dispatcherState)
    }

}
