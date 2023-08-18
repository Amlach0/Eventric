package com.eventric.ui.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.theme.EventricTheme
import kotlinx.coroutines.launch

@Composable
fun NotificationsScreen(
    navControllerForBack: NavController,
    goToEvent: (eventId: String) -> Unit,
    goToUser: (userId: String) -> Unit,
    notificationsViewModel: NotificationsViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    val notifications by notificationsViewModel.notificationsFlow.collectAsStateWithLifecycle(listOf())

    fun onAcceptEventInvite(eventId: String, userId: String) = coroutineScope.launch {
        notificationsViewModel.acceptUserInvite(
            userId = userId,
            eventId = eventId
        )
    }

    fun onRejectEventInvite(eventId: String, userId: String) = coroutineScope.launch {
        notificationsViewModel.rejectUserInvite(
            userId = userId,
            eventId = eventId
        )
    }

    fun onClearAll() = coroutineScope.launch {
        notificationsViewModel.clearAllNotifications()
    }

    fun onViewEvent(eventId: String) = goToEvent(eventId)

    fun onViewUser(userId: String) = goToUser(userId)

    EventricTheme {
        NotificationsContent(
            navControllerForBack = navControllerForBack,
            notifications = notifications,
            onAcceptEventInvite = ::onAcceptEventInvite,
            onRejectEventInvite = ::onRejectEventInvite,
            onClearAll = ::onClearAll,
            onViewEvent = ::onViewEvent,
            onViewUser = ::onViewUser
        )
    }
}