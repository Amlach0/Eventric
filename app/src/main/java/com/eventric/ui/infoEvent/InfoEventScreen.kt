package com.eventric.ui.infoEvent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.getMilliFromDate
import java.util.Calendar

@Composable
fun InfoEventScreen(
    eventId: String,
    navController: NavController,
    infoEventViewModel: InfoEventViewModel = hiltViewModel()
) {

    val userPair by infoEventViewModel.userFlow.collectAsStateWithLifecycle(null)
    val user = userPair?.second
    val event by infoEventViewModel.getEventFlow(eventId).collectAsStateWithLifecycle(null)

    val name = event?.name ?: "Loading..."
    val location = event?.location ?: ""
    val organizer = event?.organizer ?: ""
    val dateStart = event?.date?.start ?: ""
    val dateEnd = event?.date?.end ?: ""
    val dateRegistrationStart = event?.dateRegistration?.start ?: ""
    val dateRegistrationEnd = event?.dateRegistration?.end ?: ""
    val info = event?.name ?: ""
    var bookmark = user?.favoriteEvents?.contains(eventId) ?: false

    val currentTime = Calendar.getInstance().time.time
    val openRegistration = currentTime in getMilliFromDate(dateRegistrationStart, "dd/MM/yyyy hh:mm")..getMilliFromDate(dateRegistrationEnd, "dd/MM/yyyy hh:mm")
    val registrationText = "Dal $dateRegistrationStart al $dateRegistrationEnd"


    fun onBookmarkChange(){
        bookmark = !bookmark
    }

    EventricTheme {
        InfoEventContent(
            navController = navController,
            name = name,
            location = location,
            organizer = organizer,
            dateStart = dateStart,
            dateEnd = dateEnd,
            RegistrationText = registrationText,
            info = info,
            bookmarked = bookmark,
            openRegistration = openRegistration,
            onBookmarkChange = ::onBookmarkChange,
        )
    }
}