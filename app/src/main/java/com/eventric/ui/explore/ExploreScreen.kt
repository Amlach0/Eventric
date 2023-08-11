package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ExploreScreen (
    navController: NavController
) {
    fun openInfo(){
        val id = "We4P0pDw1cQspkX2uSs4"
        navController.navigate("info_event?eventId=$id")
    }

    ExploreContent(
        navController = navController,
        openInfo = ::openInfo
    )
}