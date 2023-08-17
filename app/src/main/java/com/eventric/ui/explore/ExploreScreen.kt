package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ExploreScreen (
    navController: NavController
) {
    fun openInfo(){
        val id = "lucal1a2k@gmail.com"
        navController.navigate("profile?userId=$id")
    }

    ExploreContent(
        openInfo = ::openInfo
    )
}