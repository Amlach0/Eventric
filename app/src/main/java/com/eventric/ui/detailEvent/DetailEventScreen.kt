package com.eventric.ui.detailEvent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.getMilliFromDate
import com.eventric.vo.Event
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun DetailEventScreen(
    eventId: String,
    navController: NavController,
    detailEventViewModel: DetailEventViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    detailEventViewModel.setEventId(eventId)

    val userPair by detailEventViewModel.loggedUserFlow.collectAsStateWithLifecycle(null)
    val event by detailEventViewModel.eventFlow.collectAsStateWithLifecycle(null)
    val organizer by detailEventViewModel.organizerFlow.collectAsStateWithLifecycle(null)

    val organizerName = "${organizer?.second?.name} ${organizer?.second?.surname}"
    val currentTime = Calendar.getInstance().time.time
    val isRegistrationOpen = currentTime in getMilliFromDate(event?.dateRegistration?.start ?: "", "dd/MM/yyyy hh:mm")..getMilliFromDate(event?.dateRegistration?.end ?: "", "dd/MM/yyyy hh:mm")
    val isFavorite by detailEventViewModel.isFavoriteFlow.collectAsStateWithLifecycle(false)
    val isUserSubscribed by detailEventViewModel.isSubscribedFlow.collectAsStateWithLifecycle(false)
    val isUserOrganizer by detailEventViewModel.isOrganizerFlow.collectAsStateWithLifecycle(false)
    val isOrganizerFollowed by detailEventViewModel.isOrganizerFollowedFlow.collectAsStateWithLifecycle(false)


    fun onFavoriteChange() = coroutineScope.launch {
        detailEventViewModel.changeFavourite(!isFavorite)
    }

    fun onSubscribeChange(subscribed: Boolean)  = coroutineScope.launch {
        detailEventViewModel.changeSubscribe(subscribed)
    }

    fun onFollowChange() = coroutineScope.launch {
        detailEventViewModel.changeOrganizerFollow(!isOrganizerFollowed)
    }

    EventricTheme {
        DetailEventContent(
            navController = navController,
            id = eventId,
            event = event ?: Event(),
            organizerName = organizerName,
            isFavorite = isFavorite,
            isUserOrganizer = isUserOrganizer,
            isUserSubscribed = isUserSubscribed,
            isOrganizerFollowed = isOrganizerFollowed,
            isRegistrationOpen = isRegistrationOpen,
            onFavoriteChange = ::onFavoriteChange,
            onSubscribeChange = ::onSubscribeChange,
            onFollowChange = ::onFollowChange,
            onEditClick = {},
            onInviteClick = {},
            onGoingClick = {},
        )
    }
}