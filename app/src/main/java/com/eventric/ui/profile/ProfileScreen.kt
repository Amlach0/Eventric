package com.eventric.ui.profile

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.component.SelectorItemData
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    userId: String = "",
    navController: NavController,
    goToProfile: (String) -> Unit,
    goToEvent: (String) -> Unit,
    goToEditProfile: (String) -> Unit,
    goToDispatcher: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    profileViewModel.setUserId(userId)

    val pages = listOf(
        SelectorItemData(value = "bio", label = "Bio", iconId = null),
        SelectorItemData(value = "events", label = "Events", iconId = null)
    )

    val isInHome = (userId == "")
    val user by profileViewModel.userFlow.collectAsStateWithLifecycle(null)
    val isUserFollowed by profileViewModel.isUserFollowedFlow.collectAsStateWithLifecycle(false)
    val followers by profileViewModel.followersFlow.collectAsStateWithLifecycle(listOf())
    val followed by profileViewModel.followedFlow.collectAsStateWithLifecycle(listOf())
    val organizedEvents by profileViewModel.organizedEvents.collectAsStateWithLifecycle(listOf())
    val loggedUserId by profileViewModel.loggedUserId.collectAsStateWithLifecycle("")
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.Expanded }
    )
    var selectedPage by remember { mutableStateOf(pages[0]) }
    var isFollowersSheet by remember { mutableStateOf(false) }

    fun onFollowChange() = coroutineScope.launch {
        profileViewModel.changeUserFollow(!isUserFollowed)
    }

    fun onEdit() {
        goToEditProfile(loggedUserId)
    }

    fun onUser(userId: String) = coroutineScope.launch {
        sheetState.hide()
        goToProfile(userId)
    }

    fun onLogout() {
        profileViewModel.logout()
        goToDispatcher()
    }

    fun onShowFollowed() = coroutineScope.launch {
        isFollowersSheet = false
        sheetState.show()
    }

    fun onShowFollowers() = coroutineScope.launch {
        isFollowersSheet = true
        sheetState.show()
    }

    fun onChangeSelectedPage(new: SelectorItemData) {
        selectedPage = new
    }

    fun onEvent(eventId: String) = goToEvent(eventId)


    EventricTheme {
        ProfileContent(
            navController = navController,
            user = user ?: User(),
            isInHome = isInHome,
            followed = followed,
            followers = followers,
            isUserFollowed = isUserFollowed,
            sheetState = sheetState,
            isFollowersSheet = isFollowersSheet,
            pages = pages,
            selectedPage = selectedPage,
            onFollowChange = ::onFollowChange,
            organizedEvents = organizedEvents,
            onEdit = ::onEdit,
            onLogout = ::onLogout,
            onUser = ::onUser,
            onShowFollowed = ::onShowFollowed,
            onShowFollowers = ::onShowFollowers,
            onChangeSelectedPage = ::onChangeSelectedPage,
            onEvent = ::onEvent,
        )
    }
}