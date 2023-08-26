package com.eventric.ui.events

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.component.EventCardItem
import com.eventric.ui.component.TopBarScopeInstance.Title
import com.eventric.vo.Event

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EventsContent(
    events: List<Pair<Triple<String, Boolean, Event>, Triple<Boolean, Boolean, Boolean>>>,
    getOrganized: ()-> Unit,
    getSubscribed: ()->Unit,
    getFavorite: ()->Unit,
    selected: Int,
    goToEvent: (eventId: String) -> Unit
) {
    Column(
    modifier = Modifier
        .fillMaxWidth()
)   {
        Title(
            modifier = Modifier.padding(horizontal = 11.dp),
            title = "Eventi",
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Left
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(1F)
                    .clickable { getOrganized() }
                    .background(
                        color = if (selected == 0) MaterialTheme.colors.primary else MaterialTheme.colors.background,
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected != 0) MaterialTheme.colors.onBackground else Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = null,
                    tint = if (selected == 0) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(id = R.string.organized_events_label),
                    style = MaterialTheme.typography.body1,
                    color = if (selected == 0) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .weight(1F)
                    .clickable { getSubscribed() }
                    .background(
                        color = if (selected == 1) MaterialTheme.colors.primary else MaterialTheme.colors.background,
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected != 1) MaterialTheme.colors.onBackground else Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_type_public),
                    contentDescription = null,
                    tint = if (selected == 1) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(id = R.string.subscribed_events_label),
                    style = MaterialTheme.typography.body1,
                    color = if (selected == 1) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .weight(1F)
                    .clickable { getFavorite() }
                    .background(
                        color = if (selected == 2) MaterialTheme.colors.primary else MaterialTheme.colors.background,
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected != 2) MaterialTheme.colors.onBackground else Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(R.drawable.ic_favorite_fill),
                    contentDescription = null,
                    tint = if (selected == 2) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(id = R.string.favorites_events_label),
                    style = MaterialTheme.typography.body1,
                    color = if (selected == 2) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(),
        ) {
            items(events) { (ev, sel) ->
                AnimatedVisibility(
                    visible = if(selected==0) sel.first else if(selected==1) sel.second else sel.third,
                ){
                    EventCardItem(
                        modifier = Modifier.padding(vertical = 10.dp),
                        event = ev.third,
                        organaserName = ev.third.organizer.toString(),
                        isFavorite = ev.second,
                        onClick = { goToEvent(ev.first) }
                    )
                }
            }
        }
    }
}