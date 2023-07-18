package com.eventric.ui.infoEvent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.eventric.R
import com.eventric.ui.component.CustomButtonSubmit
import com.eventric.ui.theme.EventricTheme

@Composable
fun InfoEventContent(
    name: String,
    location: String,
    organizer: String,
    dateStart: String,
    dateEnd: String,
    dateRegistrationStart: String,
    dateRegistrationEnd: String,
    info: String,
    bookmarked: Boolean,
    onBookmarkChange: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .zIndex(12F),
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Button(
                    onClick = {  },
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
                    text = "Dettagli Evento",
                    style = MaterialTheme.typography.h4,
                    fontSize = 27.sp,
                    color = MaterialTheme.colors.onBackground
                )
                Button(
                    onClick = { onBookmarkChange() },
                    colors = ButtonDefaults.buttonColors( backgroundColor = Color.Transparent ),
                ) {
                    Icon(
                        painter = painterResource(if(bookmarked) R.drawable.ic_bookmark_full else R.drawable.ic_bookmark_void),
                        contentDescription = "back",
                        tint = Color.Black,
                    )
                }
            }
            /*
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "logo",
                contentScale = ContentScale.None,
                alignment = Alignment.Center
            )
            */
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .border(border = BorderStroke(1.dp, Color.Gray), shape = CircleShape)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type_public),
                        contentDescription = "Calendar",
                        tint = MaterialTheme.colors.onBackground,
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text = "+20 going"
                    )
                    Button(
                        onClick = {},
                    )
                    { Text(text = "invite")}
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = name,
                style = MaterialTheme.typography.h4,
                fontSize = 27.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Button(
                    modifier = Modifier.padding(horizontal = 34.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Calendar",
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
                Column() {
                    Text(text = (if(dateStart == dateEnd) dateStart else dateStart + " - " + dateEnd))
                    Text(text = "Data Evento")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Button(
                    modifier = Modifier.padding(horizontal = 34.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location",
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
                Column() {
                    Text(text = location)
                    Text(text = "Luogo Evento")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Info",
                modifier = Modifier.padding(horizontal = 34.dp),
                style = MaterialTheme.typography.h2,
                fontSize = 27.sp,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = info,
                modifier = Modifier.padding(horizontal = 34.dp),
                color = MaterialTheme.colors.onBackground
            )
            Spacer(Modifier.weight(1F))
            Row(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_type_invite_only),
                    contentDescription = "InviteOnly",
                    tint = MaterialTheme.colors.onBackground,
                )
                Column() {
                    Text(text = organizer)
                    Text(text = "Organizer")
                }
            }
            CustomButtonSubmit(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
                ,
                text = stringResource(id = R.string.common_subscribe),
                onClick = {  }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun InfoEventContentPreview() {
    var name by remember { mutableStateOf("Nome Evento") }
    var location by remember { mutableStateOf("Luogo evento") }
    var organizer by remember { mutableStateOf("Organizzatore") }
    var dateStart by remember { mutableStateOf("Data inizio") }
    var dateEnd by remember { mutableStateOf("Data fine") }
    var dateRegistrationStart by remember { mutableStateOf("Data inizio registrazione") }
    var dateRegistrationEnd by remember { mutableStateOf("Data fine registrazione") }
    var info by remember { mutableStateOf("blablabla") }
    var bookmarked by remember { mutableStateOf(false) }

    EventricTheme {
        InfoEventContent(
            name = name,
            location = location,
            organizer = organizer,
            dateStart = dateStart,
            dateEnd = dateEnd,
            dateRegistrationStart = dateRegistrationStart,
            dateRegistrationEnd = dateRegistrationEnd,
            info = info,
            bookmarked = bookmarked,
            onBookmarkChange = { bookmarked = !bookmarked }
        )
    }
}
