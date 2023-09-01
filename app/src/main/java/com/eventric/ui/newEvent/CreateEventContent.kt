package com.eventric.ui.newEvent

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eventric.R
import com.eventric.ui.component.BrandAlertDialog
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.CustomButtonPrimary
import com.eventric.ui.component.CustomButtonSelector
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.component.DateAndTimeRangePickerDialog
import com.eventric.ui.component.EventCategoryItem
import com.eventric.ui.component.EventTypeItem
import com.eventric.ui.component.ImageEventPicker
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventContent(
    navControllerForBack: NavController,
    isEdit: Boolean,
    name: String,
    uriImage: Uri,
    info: String,
    location: String,
    categoryList: List<EventCategory>,
    selectedCategory: EventCategory,
    typeList: List<EventType>,
    selectedType: EventType,
    startDate: String,
    endDate: String,
    startRegistrationDate: String,
    endRegistrationDate: String,
    openDeleteDialog: Boolean,
    createErrorBannerIsVisible: Boolean,
    deleteErrorBannerIsVisible: Boolean,
    onNameChange: (String) -> Unit,
    onUriImageChange: (Uri) -> Unit,
    onInfoChanged: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSelectedCategoryChange: (EventCategory) -> Unit,
    onSelectedTypeChange: (EventType) -> Unit,
    onStartDateChanged: (String) -> Unit,
    onEndDateChanged: (String) -> Unit,
    onStartRegistrationDateChanged: (String) -> Unit,
    onEndRegistrationDateChanged: (String) -> Unit,
    onOpenDeleteDialog: () -> Unit,
    onCloseDeleteDialog: () -> Unit,
    onDelete: () -> Unit,
    onSubmit: () -> Unit,
) {
    var openDateDialog by remember { mutableStateOf(false) }
    var openRegistrationDateDialog by remember { mutableStateOf(false) }

    if (openDateDialog) {
        DateAndTimeRangePickerDialog(
            onDismiss = {
                openDateDialog = false
            },
            onStartDateSelected = {
                onStartDateChanged(it)
            },
            onEndDateSelected = {
                onEndDateChanged(it)
            }
        )
    }

    if (openRegistrationDateDialog) {
        DateAndTimeRangePickerDialog(
            onDismiss = {
                openRegistrationDateDialog = false
            },
            onStartDateSelected = {
                onStartRegistrationDateChanged(it)
            },
            onEndDateSelected = {
                onEndRegistrationDateChanged(it)
            }
        )
    }

    BrandAlertDialog(
        openDialog = openDeleteDialog,
        title = stringResource(R.string.delete_event_label),
        text = "Are you sure you want to delete this event ?",
        closeDialog = { onCloseDeleteDialog() },
        onConfirm = { onDelete() },
    )

    Scaffold(
        topBar = {
            BrandTopBar(
                left = {
                    Back(
                        navController = navControllerForBack,
                        tint = MaterialTheme.colors.onBackground
                    )
                    Title(
                        modifier = Modifier.padding(horizontal = 11.dp),
                        title = stringResource(if (isEdit) R.string.edit_event else R.string.create_event),
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Left
                    )
                },
                right = {
                    if (isEdit)
                        ActionButton(
                            iconId = R.drawable.ic_delete,
                            iconColor = MaterialTheme.colors.error,
                            onClick = { onOpenDeleteDialog() }
                        )
                }
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 30.dp)
                    .padding(top = 10.dp)
                    .padding(bottom = 20.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                // IMAGE

                ImageEventPicker(
                    uri = uriImage,
                    onUriChange = onUriImageChange
                )

                // NAME

                Spacer(modifier = Modifier.height(20.dp))
                CustomTextInput(
                    hint = stringResource(id = R.string.event_label),
                    isLastInput = false,
                    value = name,
                    onValueChange = onNameChange,
                )

                //CATEGORY

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(id = R.string.new_event_category),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )

                //Row of categories
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    items(items = categoryList) { category ->
                        EventCategoryItem(
                            category = category,
                            selected = category == selectedCategory,
                            onClick = { onSelectedCategoryChange(category) }
                        )
                    }
                }

                //LOCATION

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(id = R.string.new_event_location),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )
                CustomTextInput(
                    hint = stringResource(id = R.string.location_label),
                    isLastInput = false,
                    iconId = R.drawable.ic_location,
                    value = location,
                    onValueChange = onLocationChange
                )

                //EVENT DATE
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(R.string.new_event_date),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )

                CustomButtonSelector(
                    text = if (startDate == "" && endDate == "") stringResource(R.string.select_date) else "$startDate\n$endDate",
                    iconId = R.drawable.ic_calendar,
                    onClick = { openDateDialog = true }
                )

                //EVENT REGISTRATION DATE
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(R.string.new_event_registration_date),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )

                CustomButtonSelector(
                    text = if (startRegistrationDate == "" && endRegistrationDate == "") stringResource(
                        R.string.select_date
                    ) else "$startRegistrationDate\n$endRegistrationDate",
                    iconId = R.drawable.ic_calendar,
                    onClick = { openRegistrationDateDialog = true }
                )

                //TYPE
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(R.string.new_event_type),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )

                //Row of types
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    for (type in typeList) {
                        EventTypeItem(
                            modifier = Modifier.weight(1F),
                            type = type,
                            selected = type == selectedType,
                            onClick = { onSelectedTypeChange(type) }
                        )
                    }
                }

                // INFO

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(id = R.string.new_event_info),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )
                CustomTextInput(
                    hint = "",
                    isLastInput = false,
                    iconId = R.drawable.ic_info,
                    value = info,
                    onValueChange = onInfoChanged
                )
                Spacer(modifier = Modifier.height(80.dp))

            }

            val colorStops = arrayOf(
                0f to MaterialTheme.colors.background,
                0.04f to Color.Transparent,
                0.85f to Color.Transparent,
                0.95f to MaterialTheme.colors.background,
                1f to MaterialTheme.colors.background
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colorStops = colorStops)),
                verticalArrangement = Arrangement.Bottom
            ) {
                CustomButtonPrimary(
                    modifier = Modifier
                        .padding(15.dp),
                    text = stringResource(if (isEdit) R.string.common_edit else R.string.common_create),
                    onClick = { onSubmit() }
                )
            }

        }

        AnimatedVisibility(
            visible = createErrorBannerIsVisible || deleteErrorBannerIsVisible,
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colors.error)
            ) {
                Text(
                    text = stringResource(if (createErrorBannerIsVisible) R.string.error_new_event else R.string.error_delete_event),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onError,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                )
            }
        }
    }
}


