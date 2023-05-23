package com.eventric.ui.newEvent

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.eventric.BuildConfig
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.ErrorOperation
import com.eventric.utils.LoadingOperation
import com.eventric.utils.SuccessOperation
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@Composable
fun CreateEventScreen(
    createEventViewModel: CreateEventViewModel = hiltViewModel(),
    back: () -> Unit,
) {

    val createEventState by createEventViewModel.createEventCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(EventCategory.NoCategory) }
    var type by remember { mutableStateOf(EventType.InviteOnly) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startRegistrationDate by remember { mutableStateOf("") }
    var endRegistrationDate by remember { mutableStateOf("") }

    var sameDay by remember { mutableStateOf(false) }
    var registrationSameDay by remember { mutableStateOf(false) }


    var errorBannerIsVisible by remember { mutableStateOf(false) }


    LaunchedEffect(createEventState) {
        if (createEventState is ErrorOperation) errorBannerIsVisible = true
        //if (createEventState is SuccessOperation) onSuccess()
    }

    fun onEventNameChange(value: String) {
        name = value
    }

    fun onEventLocationChange(value: String) {
        location = value
    }

    fun onEventCategoryChange(value: Int) {
        when(value) {
            0 -> category = EventCategory.NoCategory
            1 -> category = EventCategory.Music
            2 -> category = EventCategory.Art
            3 -> category = EventCategory.Food
            4 -> category = EventCategory.Sport
        }
    }

    fun onEventTypeChange(value: Int) {
        when(value) {
            0 -> type = EventType.InviteOnly
            1 -> type = EventType.Private
            2 -> type = EventType.Public
        }
    }

    fun onSameDayChecked(){
        sameDay = !sameDay;
    }

    fun onStartDateChanged(value: String) {
        startDate = value
    }

    fun onEndDateChanged(value: String) {
        endDate = value
    }

    fun onStartRegistrationDateChanged(value: String) {
        startRegistrationDate = value
    }

    fun onEndRegistrationDate(value: String) {
        endRegistrationDate = value
    }

    fun onRegistrationSameDayChecked(){
        registrationSameDay = !registrationSameDay;
    }

    fun cancelOperation(){
        back();
    }


    fun onSubmit() = coroutineScope.launch {
        if (createEventState !is LoadingOperation) {
            try {
                if(sameDay)
                    endDate = startDate
                if(registrationSameDay){
                    //startRegistrationDate = oggi
                    endRegistrationDate = startDate
                }
                createEventViewModel.createEvent(name, location, category, type, startDate, endDate, startRegistrationDate, endRegistrationDate)
            } catch (e: Exception) {
                errorBannerIsVisible = true;
            }
        }
    }

    EventricTheme {
        CreateEventContent(
            name = name,
            location = location,
            category = category,
            type = type,
            startDate = startDate,
            endDate = endDate,
            sameDay = sameDay,
            startRegistrationDate = startRegistrationDate,
            endRegistrationDate = endRegistrationDate,
            registrationSameDay = registrationSameDay,
            errorBannerIsVisible = errorBannerIsVisible,
            onEventNameChange = ::onEventNameChange,
            onEventLocationChange = ::onEventLocationChange,
            onEventCategoryChange = ::onEventCategoryChange,
            onEventTypeChange = ::onEventTypeChange,
            onStartDateChanged = ::onStartDateChanged,
            onEndDateChanged = ::onEndDateChanged,
            onStartRegistrationDateChanged = ::onStartRegistrationDateChanged,
            onEndRegistrationDateChanged = ::onEndRegistrationDate,
            onSameDayChecked = ::onSameDayChecked,
            onRegistrationSameDayChecked = ::onRegistrationSameDayChecked,
            cancelOperation = ::cancelOperation,
            onSubmit = ::onSubmit,
        )
    }
}


