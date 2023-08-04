package com.eventric.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.eventric.R
import com.eventric.utils.getDateFromMilli

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTimeRangePickerDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onStartDateSelected: (String) -> Unit,
    onEndDateSelected: (String) -> Unit,
) {
    var pageState by remember { mutableIntStateOf(0) }

    val dateState = rememberDateRangePickerState()
    val startTimeState = rememberTimePickerState()
    val endTimeState = rememberTimePickerState()

    val startDate: String = getDateFromMilli(dateState.selectedStartDateMillis ?: 0, "dd/MM/yyyy")
    val endDate: String = getDateFromMilli(dateState.selectedEndDateMillis ?: 0, "dd/MM/yyyy")
    val startTime = "${startTimeState.hour}:${startTimeState.minute}"
    val endTime = "${endTimeState.hour}:${endTimeState.minute}"

    val titleText = when (pageState) {
        0 -> stringResource(id = R.string.select_dates)
        1 -> stringResource(id = R.string.select_time_start_date) + startDate
        2 -> stringResource(id = R.string.select_time_end_date) + endDate
        else -> ""
    }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),

        ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .size(600.dp, 510.dp)
                .padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground
                )
                Column(
                    Modifier
                        .height(410.dp)
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(1F))
                    when (pageState) {
                        0 -> DateRangePicker(state = dateState, title = {})
                        1 -> TimePicker(state = startTimeState)
                        2 -> TimePicker(state = endTimeState)
                    }
                    Spacer(modifier = Modifier.weight(1F))
                }


                Row {
                    CustomButtonSecondary(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 8.dp)
                            .weight(1F),
                        text = stringResource(if (pageState == 0) R.string.cancel else R.string.back),
                        onClick = {
                            if (pageState == 0)
                                onDismiss()
                            else pageState--
                        }
                    )


                    CustomButtonPrimary(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1F),
                        text = stringResource(if (pageState == 2) R.string.ok else R.string.forward),
                        onClick = {
                            if (pageState == 2) {
                                onStartDateSelected("$startDate $startTime")
                                onEndDateSelected("$endDate $endTime")
                                onDismiss()
                            } else pageState++
                        },
                    )
                }
            }
        }
    }
}

