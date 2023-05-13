package com.eventric.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eventric.R

@Composable
fun HomeContent(

) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { CustomBottomNavigation(navController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                onClick = {
                    navController.navigate(BottomNavItem.FAB_Add.screen_route)
                }
            ) {
                Icon(painterResource(id = BottomNavItem.FAB_Add.icon), contentDescription = BottomNavItem.FAB_Add.title)
            }
        }
    ) { paddingValues ->

        Column(Modifier.padding(paddingValues)) {
            
        }
        /*NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = BottomNavItem.Explore.screen_route
        ){

        }*/
    }
}

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    object Explore : BottomNavItem("Explore", R.drawable.ic_explore,"explore")
    object Search: BottomNavItem("Search",R.drawable.ic_search,"search")
    object Events: BottomNavItem("Events",R.drawable.ic_events,"events")
    object Profile: BottomNavItem("Profile",R.drawable.ic_profile,"profile")
    object FAB_Add: BottomNavItem("Add Event", R.drawable.ic_add, "add_event")
}

@Composable
fun CustomBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Explore,
        BottomNavItem.Search,
        BottomNavItem.Events,
        BottomNavItem.Profile
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 10.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onBackground,
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