package com.eventric.ui.newEvent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.eventric.R
import com.eventric.ui.component.CustomButtonSubmit
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.theme.EventricTheme
import com.eventric.ui.theme.Shapes
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType


@Composable
fun CreateEventContent(
    name: String,
    location: String,
    category: EventCategory,
    type: EventType,
    onEventNameChange: (String) -> Unit,
    onEventLocationChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
        {
            Button(onClick = { /*TODO*/ }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                )
                {
                    Image(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = "back",
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 34.dp),
                        text = "New Event",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 27.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                painter = painterResource(R.drawable.ic_add_photo),
                contentDescription = "add photo",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
            CustomTextInput(
                modifier = Modifier.padding(horizontal = 34.dp),
                hint = stringResource(id = R.string.event_label),
                isLastInput = false,
                value = name,
                onValueChange = onEventNameChange,
            )
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
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_date),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            var sameDay = false
            var registrationSameDay = false
            Row() {
                Text(
                    modifier = Modifier.padding(horizontal = 34.dp),
                    text = "same day",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Checkbox(
                    checked = true,
                    onCheckedChange = { sameDay = !sameDay }
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 34.dp)
            ) {
                Column() {
                    AnimatedVisibility( sameDay )
                    {
                        Text(text = "start")
                    }
                    Button(
                        onClick = {},
                    ){}
                }
                AnimatedVisibility( sameDay )
                {
                    Column() {
                        Text(text = "end")
                        Button(
                            onClick = {},
                        ){
                        }
                    }
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_registration_date),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row() {
                Text(
                    modifier = Modifier.padding(horizontal = 34.dp),
                    text = "same day",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Checkbox(
                    checked = true,
                    onCheckedChange = { registrationSameDay = !registrationSameDay }
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = 34.dp)
            ) {
                Column() {
                    AnimatedVisibility( sameDay )
                    {
                        Text(text = "start")
                    }
                    Button(
                        onClick = {},
                    ){}
                }
                Column() {
                    AnimatedVisibility( sameDay )
                    {
                        Column() {
                            Text(text = "end")
                            Button(
                                onClick = {},
                            ){
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_category),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            var pressed = remember { mutableStateOf( 0 ) }
            Row(
                modifier = Modifier.padding(horizontal = 34.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { pressed.value = 0 },
                        modifier= Modifier.size(50.dp),
                        elevation = ButtonDefaults.elevation( 9.dp ),
                        colors = if(pressed.value == 0){ ButtonDefaults.buttonColors(backgroundColor = Color.Gray)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)},
                        shape = CircleShape,
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_none),
                            contentDescription = "none",
                        )
                    }
                    Text(
                        text = "None"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { pressed.value = 1 },
                        modifier= Modifier.size(50.dp),
                        elevation = ButtonDefaults.elevation( 9.dp ),
                        colors = if(pressed.value == 1){ ButtonDefaults.buttonColors(backgroundColor = Color.Blue)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)},
                        shape = CircleShape,
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_music),
                            contentDescription = "music"
                        )
                    }
                    Text(
                        text = "Music"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { pressed.value = 2 },
                        modifier= Modifier.size(50.dp),
                        colors = if(pressed.value == 2){ ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)},
                        shape = CircleShape,
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_art),
                            contentDescription = "art"
                        )
                    }
                    Text(
                        text = "Art",
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { pressed.value = 3 },
                        modifier= Modifier.size(50.dp),
                        elevation = ButtonDefaults.elevation( 9.dp ),
                        colors = if(pressed.value == 3 ){ ButtonDefaults.buttonColors(backgroundColor = Color.Red)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)},
                        shape = CircleShape,
                    )
                    {

                        Image(
                            painter = painterResource(R.drawable.ic_category_food),
                            contentDescription = "food"
                        )
                    }

                    Text(
                        text = "Food",
                    )

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { pressed.value = 4 },
                        modifier= Modifier.size(50.dp),
                        elevation = ButtonDefaults.elevation( 9.dp ),
                        colors = if(pressed.value == 4){ ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)},
                        shape = CircleShape,
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_sport),
                            contentDescription = "sport"
                        )
                    }
                    Text(
                        text = "Sport",
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_type),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            var activeTabIndex by remember { mutableStateOf(0) }

            TabRow(
                selectedTabIndex = activeTabIndex,
                backgroundColor = Color.Transparent,
                indicator = {
                    Box(
                        Modifier
                            .tabIndicatorOffset(it[activeTabIndex])
                            .fillMaxSize()
                            .background(color = Color.Cyan)
                            .zIndex(-1F)
                    )
                },
                modifier = Modifier.padding(horizontal = 34.dp)
            )
            {
                Tab(selected = activeTabIndex == 0, onClick = { activeTabIndex = 0 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type_invite_only),
                        contentDescription = "InviteOnly",
                        tint = Color.Black,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
                Tab(selected = activeTabIndex == 1, onClick = { activeTabIndex = 1 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type_private),
                        contentDescription = "Private",
                        tint = Color.Black,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
                Tab(selected = activeTabIndex == 2, onClick = { activeTabIndex = 2 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type_public),
                        contentDescription = "Public",
                        tint = Color.Black,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
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

    EventricTheme {
        CreateEventContent(
            name = name,
            location = location,
            category = EventCategory.NoCategory,
            type = EventType.InviteOnly,
            onEventNameChange = {
                name = it
            },
            onEventLocationChange = {
                location = it
            },
            onSubmit = {}
        )
    }
}
