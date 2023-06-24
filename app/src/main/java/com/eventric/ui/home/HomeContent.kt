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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eventric.ui.component.BottomNavItem
import com.eventric.ui.component.CustomBottomNavigation
import com.eventric.ui.explore.ExploreScreen
import com.eventric.ui.explore.ExploreTopBar

@Composable
fun HomeContent(
    mainNavController: NavController
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
        bottomBar = { CustomBottomNavigation(navController = navController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                onClick = {
                    mainNavController.navigate(BottomNavItem.FabAdd.screen_route)
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
            navController = navController,
            startDestination = BottomNavItem.Explore.screen_route
        ) {

            composable(BottomNavItem.Explore.screen_route) {
                ExploreScreen()
            }
        }
    }
}


