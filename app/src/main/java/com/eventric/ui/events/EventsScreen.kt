package com.eventric.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eventric.R
import com.eventric.ui.component.SelectorItemData

@Composable
fun EventsScreen(
    goToEventDetail: (eventId: String) -> Unit,
    eventsViewModel: EventsViewModel = hiltViewModel(),
) {
    val pages = listOf(
        SelectorItemData(value = "organized", label = stringResource(R.string.organized_label), iconId = null),
        SelectorItemData(value = "subscribed", label = stringResource(R.string.subscribed_label), iconId = null),
        SelectorItemData(value = "favorites", label = stringResource(R.string.favorites_label), iconId = R.drawable.ic_favorite_fill)
    )

    val events by eventsViewModel.events.collectAsStateWithLifecycle(listOf())
    var selectedPage by remember { mutableStateOf( pages[0] ) }

    fun onChangeSelectedPage(selected: SelectorItemData) {
        selectedPage = selected
        eventsViewModel.setSelectedFilter(selectedPage.value)
    }

    fun goToEvent(eventId: String) {
        goToEventDetail(eventId)
    }

    EventsContent(
        events = events,
        pages = pages,
        selectedPage = selectedPage,
        onChangeSelectedPage = ::onChangeSelectedPage,
        goToEvent = ::goToEvent,
    )
}