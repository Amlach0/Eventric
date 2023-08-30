package com.eventric.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eventric.ui.component.SelectorItemData
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    goToEventDetail: (eventId: String) -> Unit,
    goToUserDetail: (userId: String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        SelectorItemData(value = "events", label = "Events", iconId = null),
        SelectorItemData(value = "users", label = "Users", iconId = null)
    )

    var selectedPage by remember { mutableStateOf( pages[0] ) }

    fun onChangeSelectedPage(selected: SelectorItemData) {
        selectedPage = selected
    }

    val events by searchViewModel.events.collectAsStateWithLifecycle(listOf())
    val users by searchViewModel.users.collectAsStateWithLifecycle(listOf())

    val searchWord by searchViewModel.searchWordFlow.collectAsStateWithLifecycle("")


    fun onChangeSearchWord(word: String) {
        searchViewModel.setSearchWord(word)
    }

    fun goToEvent(eventId: String) {
        goToEventDetail(eventId)
    }

    fun goToUser(userId: String) {
        goToUserDetail(userId)
    }

    fun changeFollowUser(userId: String, isFollowed: Boolean) = coroutineScope.launch{
        searchViewModel.changeOrganizerFollow(userId, !isFollowed)
    }

    SearchContent(
        events = events,
        users = users,
        pages = pages,
        selectedPage = selectedPage,
        onChangeSelectedPage = ::onChangeSelectedPage,
        searchWord = searchWord,
        onChangeSearchWord = ::onChangeSearchWord,
        goToEvent = ::goToEvent,
        goToUser = ::goToUser,
        onFollowClick = ::changeFollowUser,
    )
}