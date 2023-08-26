package com.eventric.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EventsScreen(
    goToEventDetail: (eventId: String) -> Unit,
    eventsViewModel: EventsViewModel = hiltViewModel(),
) {
    val events by eventsViewModel.getEvents().collectAsStateWithLifecycle(null)
    var selected by remember { mutableIntStateOf(0) }

    fun getOrganized(){
        selected = 0
    }

    fun getSubscribed(){
        selected = 1
    }

    fun getFavorite(){
        selected = 2
    }

    fun goToEvent(eventId: String) {
        goToEventDetail(eventId)
    }

    EventsContent(
        events = events ?: emptyList(),
        getOrganized = ::getOrganized,
        getSubscribed = ::getSubscribed,
        getFavorite = ::getFavorite,
        selected = selected,
        goToEvent = ::goToEvent
    )
}