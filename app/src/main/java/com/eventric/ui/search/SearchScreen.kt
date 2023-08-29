package com.eventric.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchScreen(
    goToEventDetail: (eventId: String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {


    val events by searchViewModel.events.collectAsStateWithLifecycle(listOf())

    val searchWord by searchViewModel.searchWordFlow.collectAsStateWithLifecycle("")


    fun onChangeSearchWord(word: String) {
        searchViewModel.setSearchWord(word)
    }

    fun goToEvent(eventId: String) {
        goToEventDetail(eventId)
    }

    SearchContent(
        events = events,
        searchWord = searchWord,
        onChangeSearchWord = ::onChangeSearchWord,
        goToEvent = ::goToEvent,
    )
}