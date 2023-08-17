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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.ErrorOperation
import com.eventric.utils.LoadingOperation
import com.eventric.utils.SuccessOperation
import com.eventric.vo.Event
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import kotlinx.coroutines.launch

@Composable
fun CreateEventScreen(
    id: String = "",
    navControllerForBack: NavController,
    createEventViewModel: CreateEventViewModel = hiltViewModel(),
    onDelete: () -> Unit = {},
    onSuccess: (eventId: String) -> Unit,
) {

    val createEventState by createEventViewModel.createEventCodeResult.collectAsState()
    val deleteEventState by createEventViewModel.deleteEventCodeResult.collectAsState()

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

    var isEdit by remember { mutableStateOf(false) }
    if (id != "") {
        createEventViewModel.setEventId(id)
        isEdit = true
    }

    val eventId by createEventViewModel.eventIdFlow.collectAsStateWithLifecycle("")
    val event by createEventViewModel.eventFlow.collectAsStateWithLifecycle(Event())
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var selectedCategory: EventCategory by remember { mutableStateOf(EventCategory.NoCategory) }
    var selectedType: EventType by remember { mutableStateOf(EventType.InviteOnly) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startRegistrationDate by remember { mutableStateOf("") }
    var endRegistrationDate by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }

    LaunchedEffect(event) {
        name = event.name ?: ""
        location = event.location ?: ""
        selectedCategory = EventCategory.fromDbString(event.category ?: "")
        selectedType = EventType.fromDbString(event.type ?: "")
        startDate = event.date?.start ?: ""
        endDate = event.date?.end ?: ""
        startRegistrationDate = event.dateRegistration?.start ?: ""
        endRegistrationDate = event.dateRegistration?.end ?: ""
        info = event.info ?: ""
    }


    var createErrorBannerIsVisible by remember { mutableStateOf(false) }
    var deleteErrorBannerIsVisible by remember { mutableStateOf(false) }


    LaunchedEffect(createEventState, deleteEventState) {
        if (createEventState is ErrorOperation) createErrorBannerIsVisible = true
        if (deleteEventState is ErrorOperation) deleteErrorBannerIsVisible = true
        if (createEventState is SuccessOperation) onSuccess(eventId)
        if (deleteEventState is SuccessOperation) onDelete()
    }

    fun closeErrorBanner() {
        createErrorBannerIsVisible = false
        deleteErrorBannerIsVisible = false
    }

    fun onEventNameChange(value: String) {
        closeErrorBanner()
        name = value
    }

    fun onEventLocationChange(value: String) {
        closeErrorBanner()
        location = value
    }

    fun onEventCategoryChange(category: EventCategory) {
        closeErrorBanner()
        selectedCategory = category
    }

    fun onEventTypeChange(type: EventType) {
        closeErrorBanner()
        selectedType = type
    }

    fun onStartDateChanged(value: String) {
        closeErrorBanner()
        startDate = value
    }

    fun onEndDateChanged(value: String) {
        closeErrorBanner()
        endDate = value
    }

    fun onStartRegistrationDateChanged(value: String) {
        closeErrorBanner()
        startRegistrationDate = value
    }

    fun onEndRegistrationDateChanged(value: String) {
        closeErrorBanner()
        endRegistrationDate = value
    }

    fun onInfoChanged(value: String) {
        closeErrorBanner()
        info = value
    }

    fun onSubmit() = coroutineScope.launch {
        if (createEventState !is LoadingOperation) {
            createEventViewModel.createOrEditEvent(
                name,
                location,
                selectedCategory,
                selectedType,
                startDate,
                endDate,
                startRegistrationDate,
                endRegistrationDate,
                info = info
            )
        }
    }

    fun onDelete() = coroutineScope.launch {
        createEventViewModel.deleteEvent()
    }

    EventricTheme {
        CreateEventContent(
            navControllerForBack = navControllerForBack,
            isEdit = isEdit,
            name = name,
            location = location,
            categoryList = categoryList,
            selectedCategory = selectedCategory,
            typeList = typeList,
            selectedType = selectedType,
            startDate = startDate,
            endDate = endDate,
            info = info,
            startRegistrationDate = startRegistrationDate,
            endRegistrationDate = endRegistrationDate,
            createErrorBannerIsVisible = createErrorBannerIsVisible,
            deleteErrorBannerIsVisible = deleteErrorBannerIsVisible,
            onNameChange = ::onEventNameChange,
            onLocationChange = ::onEventLocationChange,
            onSelectedCategoryChange = ::onEventCategoryChange,
            onSelectedTypeChange = ::onEventTypeChange,
            onStartDateChanged = ::onStartDateChanged,
            onEndDateChanged = ::onEndDateChanged,
            onStartRegistrationDateChanged = ::onStartRegistrationDateChanged,
            onEndRegistrationDateChanged = ::onEndRegistrationDateChanged,
            onInfoChanged = ::onInfoChanged,
            onSubmit = ::onSubmit,
            onDelete = ::onDelete,
        )
    }
}



