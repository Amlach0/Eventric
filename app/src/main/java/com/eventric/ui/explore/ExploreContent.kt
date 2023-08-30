package com.eventric.ui.explore

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.EventCardEmptyItem
import com.eventric.ui.component.EventCardItem
import com.eventric.vo.Event

@Composable
fun ExploreContent(
    events: List<Triple<Pair<String, Event>, Boolean, Pair<Uri, Uri>>>,
    goToEvent: (eventId: String) -> Unit,
) {

    if (events.isEmpty())
        EventCardEmptyItem(Modifier.padding(24.dp))
    else
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(events) { (eventPair, isFavourite, uriImages) ->
                val event = eventPair.second
                val id = eventPair.first
                val uriEventImage = uriImages.first
                val uriOrganizerImage = uriImages.second
                EventCardItem(
                    event = event,
                    uriEventImage = uriEventImage,
                    organiserName = event.organizer.toString(),
                    uriOrganiserImage = uriOrganizerImage,
                    isFavorite = isFavourite,
                    onClick = { goToEvent(id) },
                )
            }
        }
}

@Composable
fun ExploreTopBar(
    isNotificationActive: Boolean,
    goToNotifications: () -> Unit,
) {

    BrandTopBar(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = 33.dp,
                    bottomEnd = 33.dp
                )
            ),
        backgroundColor = MaterialTheme.colors.primary,
        paddingValues = PaddingValues(top = 15.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
        left = {
            Logo()
        },
        center = {
        },
        right = {
            ExploreActionButton(
                iconId = R.drawable.ic_notification,
                showBadge = isNotificationActive,
                onClick = { goToNotifications() }
            )
        }
    )
}

