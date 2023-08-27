package com.eventric.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eventric.ui.component.BottomNavItem
import com.eventric.ui.component.CustomBottomNavigation
import com.eventric.ui.explore.ExploreScreen
import com.eventric.ui.explore.ExploreTopBar
import com.eventric.ui.profile.ProfileScreen

@Composable
fun HomeContent(
    mainNavController: NavController,
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            when (navBackStackEntry?.destination?.route) {
                BottomNavItem.Explore.screen_route ->
                    ExploreTopBar(
                        address = "Via Esempio, 69",
                        goToMap = {},
                        goToNotification = {}
                    )
            }


        },
        bottomBar = { CustomBottomNavigation(navController = homeNavController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                onClick = {
                    mainNavController.navigate(BottomNavItem.FabAdd.screen_route) {
                        popUpTo(homeNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(
                    painterResource(id = BottomNavItem.FabAdd.icon),
                    contentDescription = BottomNavItem.FabAdd.title
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = homeNavController,
            startDestination = BottomNavItem.Explore.screen_route
        ) {

            composable(BottomNavItem.Explore.screen_route) {
                ExploreScreen(
                    goToEventDetail = { mainNavController.navigate("info_event?eventId=$it") },
                )
            }
            composable(BottomNavItem.Profile.screen_route) {
                ProfileScreen(
                    navController = mainNavController,
                    goToProfile = { userId -> mainNavController.navigate("profile?userId=$userId") },
                    goToEvent = {},
                    goToEditProfile = { userId -> mainNavController.navigate("edit_user?userId=$userId") },
                    goToDispatcher = { mainNavController.navigate("dispatcher"){
                        popUpTo(mainNavController.graph.findStartDestination().id)
                    } }
                )
            }
        }
    }
}


