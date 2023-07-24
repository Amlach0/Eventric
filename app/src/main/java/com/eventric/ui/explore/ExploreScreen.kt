package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eventric.ui.infoEvent.InfoEventScreen

@Composable
fun ExploreScreen (
    navController: NavController
) {
    fun openInfo(){
        navController.navigate("info_event") {
            navController.graph.startDestinationRoute?.let { screen_route ->
                popUpTo(screen_route) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    ExploreContent(
        navController = navController,
        openInfo = ::openInfo
    )
}