package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel = hiltViewModel(),
) {
    ExploreContent()
}