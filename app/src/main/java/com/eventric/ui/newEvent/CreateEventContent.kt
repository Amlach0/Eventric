package com.eventric.ui.newEvent

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eventric.R
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.CustomButtonPrimary
import com.eventric.ui.component.CustomButtonSelector
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.component.DateAndTimeRangePickerDialog
import com.eventric.ui.component.EventCategoryItem
import com.eventric.ui.component.EventTypeItem
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventContent(
    navController: NavController,
    name: String,
    location: String,
    categoryList: List<EventCategory>,
    selectedCategory: EventCategory,
    typeList: List<EventType>,
    selectedType: EventType,
    startDate: String,
    endDate: String,
    startRegistrationDate: String,
    endRegistrationDate: String,
    errorBannerIsVisible: Boolean,
    onNameChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSelectedCategoryChange: (EventCategory) -> Unit,
    onSelectedTypeChange: (EventType) -> Unit,
    onStartDateChanged: (String) -> Unit,
    onEndDateChanged: (String) -> Unit,
    onStartRegistrationDateChanged: (String) -> Unit,
    onEndRegistrationDateChanged: (String) -> Unit,
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

    Scaffold(
        topBar = {
            BrandTopBar(
                left = {
                    Back(
                        navController = navController,
                        tint = MaterialTheme.colors.onBackground
                    )
                    Title(
                        modifier = Modifier.padding(horizontal = 11.dp),
                        title = stringResource(R.string.create_event),
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Left
                    )
                },
            )
        }
    ) {

        AnimatedVisibility(
            visible = errorBannerIsVisible,
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colors.error)
            ) {
                Text(
                    text = stringResource(R.string.error_login),
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                )
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1F)
                    .padding(horizontal = 30.dp)
                    .padding(top = 10.dp)
                    .padding(bottom = 20.dp)
            ) {
                // TODO add image


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
                    icon = R.drawable.ic_location,
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

                //Row of categories
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
            }

            CustomButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                text = stringResource(id = R.string.common_create),
                onClick = { onSubmit() }
            )
        }
    }
}


