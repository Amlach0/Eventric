package com.eventric.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eventric.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Explore : BottomNavItem("Explore", R.drawable.ic_explore, "explore")
    object Search : BottomNavItem("Search", R.drawable.ic_search, "search")
    object Events : BottomNavItem("Events", R.drawable.ic_events, "events")
    object Profile : BottomNavItem("Profile", R.drawable.ic_profile, "profile")
    object FabAdd : BottomNavItem("Add Event", R.drawable.ic_add, "new_event")
}

@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val items = listOf(
        BottomNavItem.Explore,
        BottomNavItem.Search,
        BottomNavItem.Events,
        BottomNavItem.Profile
    )
    BottomNavigation(
        modifier = modifier.height(64.dp),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 10.dp
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(23.dp),
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.caption
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}