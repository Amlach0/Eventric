package com.eventric.ui.newEvent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.eventric.R
import com.eventric.ui.component.CustomButtonSubmit
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun CreateEventContent(
    name: String,
    location: String,
    category: EventCategory,
    type: EventType,
    startDate: String,
    endDate: String,
    sameDay: Boolean,
    startRegistrationDate: String,
    endRegistrationDate: String,
    registrationSameDay: Boolean,
    errorBannerIsVisible: Boolean,
    onEventNameChange: (String) -> Unit,
    onEventLocationChange: (String) -> Unit,
    onEventCategoryChange: (Int) -> Unit,
    onEventTypeChange: (Int) -> Unit,
    onStartDateChanged: (String) -> Unit,
    onEndDateChanged: (String) -> Unit,
    onStartRegistrationDateChanged: (String) -> Unit,
    onEndRegistrationDateChanged: (String) -> Unit,
    onSameDayChecked: () -> Unit,
    onRegistrationSameDayChecked: () -> Unit,
    cancelOperation: () -> Unit,
    onSubmit: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(androidx.compose.material.MaterialTheme.colors.primary)
                .zIndex(12F),
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            Button(
                onClick = cancelOperation,
                colors = ButtonDefaults.buttonColors( backgroundColor = Color.Transparent ),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "back",
                    tint = Color.Black,
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = "New Event",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
        {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                painter = painterResource(R.drawable.ic_add_photo),
                contentDescription = "add photo",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
            AnimatedVisibility(
                visible = errorBannerIsVisible,
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(androidx.compose.material.MaterialTheme.colors.error)
                ) {
                    Text(
                        text = stringResource(R.string.error_login),
                        style = androidx.compose.material.MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
            CustomTextInput(
                modifier = Modifier.padding(horizontal = 34.dp),
                hint = stringResource(id = R.string.event_label),
                isLastInput = false,
                value = name,
                onValueChange = onEventNameChange,
            )


            //CATEGORY

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_category),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            //Row of buttons
            Row(
                modifier = Modifier.padding(horizontal = 34.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { onEventCategoryChange(0) },
                        modifier= Modifier.size(50.dp),
                        colors = if(category == EventCategory.NoCategory){ ButtonDefaults.buttonColors(backgroundColor = Color.Gray)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.White)},
                        shape = CircleShape,
                    )
                    {
                        Icon(
                            painter = painterResource(R.drawable.ic_category_none),
                            contentDescription = stringResource(id = R.string.category_none)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.category_none)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { onEventCategoryChange(1) },
                        modifier= Modifier.size(50.dp),
                        colors = if(category == EventCategory.Music){ ButtonDefaults.buttonColors(backgroundColor = Color.Blue)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.White)},
                        shape = CircleShape,
                    )
                    {
                        Icon(
                            painter = painterResource(R.drawable.ic_category_music),
                            contentDescription = stringResource(id = R.string.category_music)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.category_music)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { onEventCategoryChange(2) },
                        modifier= Modifier.size(50.dp),
                        colors = if(category == EventCategory.Art){ ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.White)},
                        shape = CircleShape,
                    )
                    {
                        Icon(
                            painter = painterResource(R.drawable.ic_category_art),
                            contentDescription = stringResource(id = R.string.category_none)
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.category_art),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { onEventCategoryChange(3) },
                        modifier= Modifier.size(50.dp),
                        colors = if(category == EventCategory.Food){ ButtonDefaults.buttonColors(backgroundColor = Color.Red)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.White)},
                        shape = CircleShape,
                    )
                    {

                        Icon(
                            painter = painterResource(R.drawable.ic_category_food),
                            contentDescription = stringResource(id = R.string.category_food)
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.category_food),
                    )

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { onEventCategoryChange(4) },
                        modifier= Modifier.size(50.dp),
                        colors = if(category == EventCategory.Sport){ ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.White)},
                        shape = CircleShape,
                    )
                    {
                        Icon(
                            painter = painterResource(R.drawable.ic_category_sport),
                            contentDescription = stringResource(id = R.string.category_none),
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.category_sport),
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_location),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            CustomTextInput(
                modifier = Modifier.padding(horizontal = 34.dp),
                hint = stringResource(id = R.string.location_label),
                isLastInput = false,
                icon = R.drawable.ic_location,
                value = location,
                onValueChange = onEventLocationChange
            )
            Spacer(modifier = Modifier.height(20.dp))

            // DATA

            val context = LocalContext.current
            val calendar = Calendar.getInstance()

            // data inizio

            var selectedStartDateText by remember { mutableStateOf("") }
            var selectedStartTimeText by remember { mutableStateOf("") }


            //recupero l'ora attuale
            val Hour = calendar[Calendar.HOUR_OF_DAY]
            val Minute = calendar[Calendar.MINUTE]

            //creo il dialog per la selezione dell'orario
            val startTimePicker = TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    selectedStartTimeText = "$selectedHour:$selectedMinute"; onStartDateChanged(selectedStartDateText + " "+selectedStartTimeText)
                }, Hour, Minute, false
            )


            // recupero la data attuale
            val Year = calendar[Calendar.YEAR]
            val Month = calendar[Calendar.MONTH]
            val DayOfMonth = calendar[Calendar.DAY_OF_MONTH]

            //creo il dialog per la selezione della data
            val startDatePicker = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    selectedStartDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"; startTimePicker.show()
                }, Year, Month, DayOfMonth
            )
            startDatePicker.datePicker.minDate = calendar.timeInMillis


            // data fine

            var selectedEndDateText by remember { mutableStateOf("") }
            var selectedEndTimeText by remember { mutableStateOf("") }

            val endTimePicker = TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    selectedEndTimeText = "$selectedHour:$selectedMinute"; onEndDateChanged(selectedEndDateText+" "+selectedEndTimeText);
                }, Hour, Minute, false
            )

            //creo il dialog per la selezione della data
            val endDatePicker = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    selectedEndDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"; endTimePicker.show()
                }, Year, Month, DayOfMonth
            )

            endDatePicker.datePicker.minDate = calendar.timeInMillis

            // DATA ISCRIZIONE

            // data inizio

            var selectedStartRegistrationDateText by remember { mutableStateOf("") }
            var selectedStartRegistrationTimeText by remember { mutableStateOf("") }


            //recupero l'ora attuale
            val startRegistrationTimePicker = TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    selectedStartRegistrationTimeText = "$selectedHour:$selectedMinute"; onStartRegistrationDateChanged(selectedStartRegistrationDateText+" "+selectedStartRegistrationTimeText)
                }, Hour, Minute, false
            )

            //creo il dialog per la selezione della data
            val startRegistrationDatePicker = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    selectedStartRegistrationDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"; startRegistrationTimePicker.show()
                }, Year, Month, DayOfMonth
            )
            startRegistrationDatePicker.datePicker.minDate = calendar.timeInMillis

            // data fine

            var selectedEndRegistrationDateText by remember { mutableStateOf("") }
            var selectedEndRegistrationTimeText by remember { mutableStateOf("") }

            val endRegistrationTimePicker = TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    selectedEndRegistrationTimeText = "$selectedHour:$selectedMinute"; onEndRegistrationDateChanged(selectedEndRegistrationDateText+" "+selectedEndRegistrationTimeText)
                }, Hour, Minute, false
            )

            //creo il dialog per la selezione della data
            val endRegistrationDatePicker = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    selectedEndRegistrationDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"; endRegistrationTimePicker.show()
                }, Year, Month, DayOfMonth
            )

            endRegistrationDatePicker.datePicker.minDate = calendar.timeInMillis


            Row() {
                Column(
                    modifier = Modifier
                        .padding(start = 34.dp)
                        .width(150.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.new_event_date),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 27.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Checkbox(
                            checked = sameDay,
                            onCheckedChange = { onSameDayChecked() },
                            enabled = true,
                        )
                        Text(
                            text = "same",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(text = "inizio")
                    Button(
                        onClick = {
                            startDatePicker.show()
                        }
                    ) {
                        Text(
                            fontSize = 10.sp,
                            text =
                            if(selectedStartDateText.isEmpty() && selectedStartTimeText.isEmpty()){
                                "Seleziona data e ora"
                                }else{
                                    if(selectedStartDateText.isNotEmpty())
                                    {
                                        if(selectedStartTimeText.isNotEmpty())
                                        {
                                            selectedStartDateText + " " +selectedStartTimeText
                                        }
                                        else
                                        {
                                            "Seleziona ora"
                                        }
                                    }
                                    else{
                                        "Seleziona giorno"
                                    }
                            }
                        )
                    }
                    Text(
                        text = "fine"
                    )
                    Button(
                        onClick = {
                            if(!sameDay){endDatePicker.show()}
                            else{endTimePicker.show()}
                        }
                    ) {
                        Text(
                            fontSize = 10.sp,
                            text = if(sameDay){
                                if(selectedEndTimeText.isEmpty()){
                                    "Seleziona ora"
                                }else{
                                    selectedStartDateText + " "+selectedEndTimeText
                                }
                            }else {
                                if (selectedEndDateText.isEmpty() && selectedEndTimeText.isEmpty()) {
                                    "Seleziona data e ora"
                                } else {
                                    if (selectedEndDateText.isNotEmpty()) {
                                        if (selectedEndTimeText.isNotEmpty()) {
                                            selectedEndDateText + " " + selectedEndTimeText
                                        } else {
                                            "Seleziona ora"
                                        }
                                    } else {
                                        "Seleziona giorno"
                                    }
                                }
                            }
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 34.dp)
                        .width(150.dp),
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.new_event_date),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 27.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Left
                        )
                        Checkbox(
                            checked = registrationSameDay,
                            onCheckedChange = { onRegistrationSameDayChecked() },
                            enabled = true,
                        )
                        Text(
                            text = "same",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        textAlign = TextAlign.Left,
                        text = if(registrationSameDay){
                                    if(selectedStartDateText.isNotEmpty() && selectedStartTimeText.isNotEmpty()){
                                        "L'iscrizione aprirà oggi e si chiuderà il $selectedStartDateText alle $selectedStartTimeText"
                                    } else {
                                        if (selectedStartDateText.isNotEmpty()) {
                                            "L'iscrizione aprirà oggi e si chiuderà il $selectedStartDateText, seleziona l'oriario"
                                        } else {
                                            "L'iscrizione aprirà oggi, seleziona la data di inizio evento"
                                        }
                                    }
                                }else{
                                    "inizio"
                                },
                    )
                    AnimatedVisibility(visible = !registrationSameDay) {
                        Button(
                            onClick = {
                                startRegistrationDatePicker.show()
                            }
                        ) {
                            Text(
                                fontSize = 10.sp,
                                text =
                                    if (selectedStartRegistrationDateText.isEmpty() && selectedStartRegistrationTimeText.isEmpty()) {
                                        "Seleziona data e ora"
                                    } else {
                                        if (selectedStartRegistrationDateText.isNotEmpty()) {
                                            if (selectedStartRegistrationTimeText.isNotEmpty()) {
                                                selectedStartRegistrationDateText + " " + selectedStartRegistrationTimeText
                                            } else {
                                                "Seleziona ora"
                                            }
                                        } else {
                                            "Seleziona giorno"
                                        }
                                    }
                            )
                        }
                    }
                    AnimatedVisibility(visible = !registrationSameDay) {
                        Text(text = "fine")
                    }
                    AnimatedVisibility(visible = !registrationSameDay) {
                        Button(
                            onClick = {
                                endRegistrationDatePicker.show()
                            }
                        ) {
                            Text(
                                fontSize = 10.sp,
                                text =
                                if (selectedEndRegistrationDateText.isEmpty() && selectedEndRegistrationTimeText.isEmpty()) {
                                    "Seleziona data e ora"
                                } else {
                                    if (selectedEndRegistrationDateText.isNotEmpty()) {
                                        if (selectedEndRegistrationTimeText.isNotEmpty()) {
                                            selectedEndRegistrationDateText + " " + selectedEndRegistrationTimeText
                                        } else {
                                            "Seleziona ora"
                                        }
                                    } else {
                                        "Seleziona giorno"
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_type),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            TabRow(
                selectedTabIndex = type.ordinal,
                backgroundColor = Color.LightGray,
                indicator = {
                    Box(
                        Modifier
                            .tabIndicatorOffset(it[type.ordinal])
                            .fillMaxSize()
                            .background(color = Color.White)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .zIndex(-1F)
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .border(5.dp, Color.LightGray, shape = RoundedCornerShape(20.dp)),

            )
            {
                Tab(selected = type == EventType.InviteOnly, onClick = { onEventTypeChange(0) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_type_invite_only),
                            contentDescription = "InviteOnly",
                            tint = Color.Black
                        )
                        AnimatedVisibility(visible = (type == EventType.InviteOnly)) {
                            Text(text = "su invito")
                        }
                    }
                }
                Tab(selected = type == EventType.Private, onClick = { onEventTypeChange(1) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_type_private),
                            contentDescription = "Private",
                            tint = Color.Black
                        )
                        AnimatedVisibility(visible = (type == EventType.Private)) {
                            Text(text = "privato")
                        }
                    }
                }
                Tab(selected = type == EventType.Public, onClick = { onEventTypeChange(2) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_type_public),
                            contentDescription = "Public",
                            tint = Color.Black,
                        )
                        AnimatedVisibility(visible = (type == EventType.Public)) {
                            Text(text = "pubblico")
                        }
                    }
                }
            }

            CustomButtonSubmit(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.common_create),
                onClick = { onSubmit() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventContentPreview() {

    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(EventCategory.NoCategory) }
    var type by remember { mutableStateOf(EventType.InviteOnly)}

    var startDate by remember { mutableStateOf("")}
    var endDate by remember { mutableStateOf("")}
    var startRegistrationDate by remember { mutableStateOf("")}
    var endRegistrationDate by remember { mutableStateOf("")}


    EventricTheme {
        CreateEventContent(
            name = name,
            location = location,
            category = category,
            type = type,
            startDate = startDate,
            endDate = endDate,
            sameDay = false,
            startRegistrationDate = startRegistrationDate,
            endRegistrationDate = endRegistrationDate,
            registrationSameDay = false,
            errorBannerIsVisible = false,
            onEventNameChange = {
                name = it
            },
            onEventLocationChange = {
                location = it
            },
            onEventCategoryChange = {},
            onEventTypeChange = {},
            onStartDateChanged = {},
            onEndDateChanged = {},
            onStartRegistrationDateChanged = {},
            onEndRegistrationDateChanged = {},
            onSameDayChecked = {},
            onRegistrationSameDayChecked = {},
            cancelOperation = {},
            onSubmit = {}
        )
    }
}
