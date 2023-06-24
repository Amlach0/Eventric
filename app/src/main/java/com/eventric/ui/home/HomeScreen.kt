package com.eventric.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen (
    mainNavController: NavController
) {
    HomeContent(
        mainNavController = mainNavController
    )
}