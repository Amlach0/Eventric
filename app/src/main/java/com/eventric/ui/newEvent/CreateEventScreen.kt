package com.eventric.ui.newEvent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.ErrorOperation
import com.eventric.utils.LoadingOperation
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import kotlinx.coroutines.launch

@Composable
fun CreateEventScreen(
    navController: NavController,
    createEventViewModel: CreateEventViewModel = hiltViewModel(),
) {

    val createEventState by createEventViewModel.createEventCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val categoryList = listOf(
        EventCategory.NoCategory,
        EventCategory.Music,
        EventCategory.Art,
        EventCategory.Food,
        EventCategory.Sport,
    )

    val typeList = listOf(
        EventType.InviteOnly,
        EventType.Private,
        EventType.Public
    )

    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var selectedCategory: EventCategory by remember { mutableStateOf(EventCategory.NoCategory) }
    var selectedType: EventType by remember { mutableStateOf(EventType.InviteOnly) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startRegistrationDate by remember { mutableStateOf("") }
    var endRegistrationDate by remember { mutableStateOf("") }


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

    fun onEventCategoryChange(category: EventCategory) {
        selectedCategory = category
    }

    fun onEventTypeChange(type: EventType) {
        selectedType = type
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


    fun onSubmit() = coroutineScope.launch {
        if (createEventState !is LoadingOperation) {
            createEventViewModel.createEvent(
                name,
                location,
                selectedCategory,
                selectedType,
                startDate,
                endDate,
                startRegistrationDate,
                endRegistrationDate
            )
        }
    }

    EventricTheme {
        CreateEventContent(
            navController = navController,
            name = name,
            location = location,
            categoryList = categoryList,
            selectedCategory = selectedCategory,
            typeList = typeList,
            selectedType = selectedType,
            startDate = startDate,
            endDate = endDate,
            startRegistrationDate = startRegistrationDate,
            endRegistrationDate = endRegistrationDate,
            errorBannerIsVisible = errorBannerIsVisible,
            onNameChange = ::onEventNameChange,
            onLocationChange = ::onEventLocationChange,
            onSelectedCategoryChange = ::onEventCategoryChange,
            onSelectedTypeChange = ::onEventTypeChange,
            onStartDateChanged = ::onStartDateChanged,
            onEndDateChanged = ::onEndDateChanged,
            onStartRegistrationDateChanged = ::onStartRegistrationDateChanged,
            onEndRegistrationDateChanged = ::onEndRegistrationDate,
            onSubmit = ::onSubmit,
        )
    }
}



