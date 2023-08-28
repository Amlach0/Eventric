package com.eventric.ui.detailEvent

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
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.getMilliFromDate
import com.eventric.vo.Event
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailEventScreen(
    eventId: String,
    navControllerForBack: NavController,
    goToEditEvent: () -> Unit,
    goToProfile: (String) -> Unit,
    detailEventViewModel: DetailEventViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    detailEventViewModel.setEventId(eventId)

    val event by detailEventViewModel.eventFlow.collectAsStateWithLifecycle(null)
    val organizer by detailEventViewModel.organizerFlow.collectAsStateWithLifecycle(null)

    val organizerName = "${organizer?.second?.name} ${organizer?.second?.surname}"
    val currentTime = Calendar.getInstance().time.time
    val isRegistrationOpen = currentTime in getMilliFromDate(event?.dateRegistration?.start ?: "", "dd/MM/yyyy hh:mm")..getMilliFromDate(event?.dateRegistration?.end ?: "", "dd/MM/yyyy hh:mm")
    val isFavorite by detailEventViewModel.isFavoriteFlow.collectAsStateWithLifecycle(false)
    val isUserSubscribed by detailEventViewModel.isSubscribedFlow.collectAsStateWithLifecycle(false)
    val isUserOrganizer by detailEventViewModel.isOrganizerFlow.collectAsStateWithLifecycle(false)
    val isOrganizerFollowed by detailEventViewModel.isOrganizerFollowedFlow.collectAsStateWithLifecycle(false)
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.Expanded }
    )
    var isInviteSheet by remember { mutableStateOf(false) }
    val subscribedUsers by detailEventViewModel.subscribedUsersFlow.collectAsStateWithLifecycle(listOf())
    val invitableUsers by detailEventViewModel.invitableUsersFlow.collectAsStateWithLifecycle(listOf())


    fun onFavoriteChange() = coroutineScope.launch {
        detailEventViewModel.changeFavourite(!isFavorite)
    }

    fun onSubscribeChange(subscribed: Boolean)  = coroutineScope.launch {
        detailEventViewModel.changeSubscribe(subscribed)
    }

    fun onFollowChange() = coroutineScope.launch {
        detailEventViewModel.changeOrganizerFollow(!isOrganizerFollowed)
    }

    fun onUserInviteChange(userId: String) = coroutineScope.launch {
        detailEventViewModel.changeUserInvite(userId, invitableUsers.findLast { it.first== userId }?.second != true)
    }

    fun onInvite() = coroutineScope.launch {
        sheetState.show()
        isInviteSheet = true
    }

    fun onGoing() = coroutineScope.launch {
        sheetState.show()
        isInviteSheet = false
    }

    fun onEdit() = goToEditEvent()

    fun onUser(userId: String) = coroutineScope.launch{
        sheetState.hide()
        goToProfile(userId)
    }
    fun onOrganizer() = goToProfile(organizer!!.first)

    EventricTheme {
        DetailEventContent(
            navController = navControllerForBack,
            event = event ?: Event(),
            organizerName = organizerName,
            isFavorite = isFavorite,
            isRegistrationOpen = isRegistrationOpen,
            isUserOrganizer = isUserOrganizer,
            isUserSubscribed = isUserSubscribed,
            isOrganizerFollowed = isOrganizerFollowed,
            sheetState = sheetState,
            isInviteSheet = isInviteSheet,
            subscribedUsers = subscribedUsers,
            invitableUsers = invitableUsers,
            onEdit = ::onEdit,
            onUser = ::onUser,
            onOrganizer = ::onOrganizer,
            onInvite = ::onInvite,
            onGoing = ::onGoing,
            onFavoriteChange = ::onFavoriteChange,
            onFollowChange = ::onFollowChange,
            onUserInviteChange = ::onUserInviteChange,
            onSubscribeChange = ::onSubscribeChange,
        )
    }
}