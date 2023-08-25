package com.eventric.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    mainNavController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val isNotificationActive by homeViewModel.notificationsPresents.collectAsStateWithLifecycle(false)

    HomeContent(
        mainNavController = mainNavController,
        isNotificationActive = isNotificationActive
    )
}