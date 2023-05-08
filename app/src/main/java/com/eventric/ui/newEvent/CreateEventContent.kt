package com.eventric.ui.newEvent

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
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
import com.eventric.R
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventContent(
    name: String,
    location: String,
    category: EventCategory,
    type: EventType
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
                value = "name"
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
                value = ""
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.new_event_date),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
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
                modifier = Modifier.padding(horizontal = 34.dp)
            ) {
                Column() {
                    Button(
                        onClick = { pressed.value = 0 },
                        colors = if(pressed.value == 0){ ButtonDefaults.buttonColors(backgroundColor = Color.Gray)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)},
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_none),
                            contentDescription = "none"
                        )
                    }
                    Text(
                        text = "None",
                        fontSize = 15.sp
                    )
                }
                Column() {
                    Button(
                        onClick = { pressed.value = 1 },
                        colors = if(pressed.value == 1){ ButtonDefaults.buttonColors(backgroundColor = Color.Blue)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)},
                       )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_music),
                            contentDescription = "music"
                        )
                    }
                    Text(
                        text = "Music",
                        fontSize = 15.sp
                    )
                }
                var color = remember { mutableStateOf( Color.White) }

                Column() {
                    Button(
                        onClick = { pressed.value = 2 },
                        colors = if(pressed.value == 2){ ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)},
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_art),
                            contentDescription = "art"
                        )
                    }
                    Text(
                        text = "Art",
                        fontSize = 15.sp
                    )
                }
                Column() {
                    Button(
                        onClick = { pressed.value = 3 },
                        colors = if(pressed.value == 3 ){ ButtonDefaults.buttonColors(backgroundColor = Color.Red)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)},
                    )
                    {

                        Image(
                            painter = painterResource(R.drawable.ic_category_food),
                            contentDescription = "food"
                        )
                    }

                    Text(
                        text = "Food",
                        fontSize = 15.sp
                    )

                }
                Column() {
                    Button(
                        onClick = { pressed.value = 4 },
                        colors = if(pressed.value == 4){ ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)}else{ ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)},
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_category_sport),
                            contentDescription = "sport"
                        )
                    }
                    Text(
                        text = "Sport",
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
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
            Row(
                modifier = Modifier.padding(horizontal = 34.dp)
            ) {
                Button(onClick = { /*TODO*/ })
                {
                }
                Button(onClick = { /*TODO*/ })
                {
                }
                Button(onClick = { /*TODO*/ })
                {
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }
            )
            {
                Text(
                    text = "Prosegui",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 27.sp,
                )
            }
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
            type = EventType.InviteOnly
        )
    }
}
