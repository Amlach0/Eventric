package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel = hiltViewModel(),
) {
    val events by exploreViewModel.getEvents().collectAsStateWithLifecycle(listOf())

    ExploreContent(
        events = events
    )
}