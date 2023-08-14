package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ExploreScreen (
    navController: NavController
) {
    fun openInfo(){
        val id = "zGsw8S6v9ljTtJfQxTaC"
        navController.navigate("info_event?eventId=$id")
    }

    ExploreContent(
        openInfo = ::openInfo
    )
}