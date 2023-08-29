package com.eventric.ui.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExploreScreen(
    goToEventDetail: (eventId: String) -> Unit,
    exploreViewModel: ExploreViewModel = hiltViewModel(),
) {
    val events by exploreViewModel.events.collectAsStateWithLifecycle(listOf())

    fun goToEvent(eventId: String) {
        goToEventDetail(eventId)
    }

    ExploreContent(
        events = events,
        goToEvent = ::goToEvent
    )
}