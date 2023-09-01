package com.eventric.ui.newEvent

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.component.FullScreenLoader
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
    val mContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    fun mToast(text: String){
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show()
    }

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
    val dbUriImage by createEventViewModel.uriImageFlow.collectAsStateWithLifecycle(Uri.EMPTY)
    var name by remember { mutableStateOf("") }
    var uriImage by remember { mutableStateOf(Uri.EMPTY) }
    var location by remember { mutableStateOf("") }
    var selectedCategory: EventCategory by remember { mutableStateOf(EventCategory.NoCategory) }
    var selectedType: EventType by remember { mutableStateOf(EventType.InviteOnly) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startRegistrationDate by remember { mutableStateOf("") }
    var endRegistrationDate by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var openDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(event, dbUriImage) {
        name = event.name ?: ""
        location = event.location ?: ""
        uriImage = dbUriImage
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
        if (createEventState is SuccessOperation)
        {
            mToast(if (isEdit) "Event modified" else "Event created")
            onSuccess(eventId)
        }
        if (deleteEventState is SuccessOperation)
        {
            mToast("Deleted Event")
            onDelete()
        }
    }

    fun closeErrorBanner() {
        createErrorBannerIsVisible = false
        deleteErrorBannerIsVisible = false
    }

    fun onEventNameChange(value: String) {
        closeErrorBanner()
        name = value
    }

    fun onUriImageChange(value: Uri) {
        closeErrorBanner()
        uriImage = value
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

    fun onOpenDeleteDialog() { openDeleteDialog = true }

    fun onCloseDeleteDialog() { openDeleteDialog = false }

    fun onSubmit() = coroutineScope.launch {
        if (createEventState !is LoadingOperation) {
            createEventViewModel.createOrEditEvent(
                name = name,
                uriImage = uriImage,
                location = location,
                category = selectedCategory,
                type = selectedType,
                startDate = startDate,
                endDate = endDate,
                startRegistrationDate = startRegistrationDate,
                endRegistrationDate = endRegistrationDate,
                info = info
            )
        }
    }

    fun onDelete() = coroutineScope.launch {
        createEventViewModel.deleteEvent()
        mToast("Evento eliminato")
    }

    EventricTheme {
        if (createEventState is LoadingOperation || createEventState is SuccessOperation || deleteEventState is LoadingOperation || deleteEventState is SuccessOperation)
            FullScreenLoader()
        else
            CreateEventContent(
                navControllerForBack = navControllerForBack,
                isEdit = isEdit,
                name = name,
                uriImage = uriImage,
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
                openDeleteDialog = openDeleteDialog,
                createErrorBannerIsVisible = createErrorBannerIsVisible,
                deleteErrorBannerIsVisible = deleteErrorBannerIsVisible,
                onNameChange = ::onEventNameChange,
                onUriImageChange = ::onUriImageChange,
                onLocationChange = ::onEventLocationChange,
                onSelectedCategoryChange = ::onEventCategoryChange,
                onSelectedTypeChange = ::onEventTypeChange,
                onStartDateChanged = ::onStartDateChanged,
                onEndDateChanged = ::onEndDateChanged,
                onStartRegistrationDateChanged = ::onStartRegistrationDateChanged,
                onEndRegistrationDateChanged = ::onEndRegistrationDateChanged,
                onInfoChanged = ::onInfoChanged,
                onOpenDeleteDialog = ::onOpenDeleteDialog,
                onCloseDeleteDialog = ::onCloseDeleteDialog,
                onSubmit = ::onSubmit,
                onDelete = ::onDelete,
            )
    }
}



