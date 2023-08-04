package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ExploreScreen (
    navController: NavController
) {
    fun openInfo(){
        val id = "B9pEC7HuQUFua2HAUDwY"
        navController.navigate("info_event?eventId=$id")
    }

    ExploreContent(
        navController = navController,
        openInfo = ::openInfo
    )
}