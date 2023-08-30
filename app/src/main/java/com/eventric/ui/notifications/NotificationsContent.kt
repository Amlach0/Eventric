package com.eventric.ui.notifications

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eventric.R
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.NotificationItem
import com.eventric.vo.Event
import com.eventric.vo.User

@Composable
fun NotificationsContent(
    navControllerForBack: NavController,
    notifications: List<Triple<Triple<String, User, Uri>, Pair<String, String>, Pair<String, Event>>>,
    onAcceptEventInvite: (eventId: String, userId: String) -> Unit,
    onRejectEventInvite: (eventId: String, userId: String) -> Unit,
    onClearAll: () -> Unit,
    onViewEvent: (eventId: String) -> Unit,
    onViewUser: (userId: String) -> Unit,
) {
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
                        title = stringResource(R.string.notifications_label),
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Left
                    )
                },
                right = {
                    ActionButton(
                        iconId = R.drawable.ic_delete,
                        iconColor = MaterialTheme.colors.error,
                        backgroundColor = MaterialTheme.colors.error.copy(alpha = 0.1f),
                        text = stringResource(R.string.clear_all_label),
                        onClick = { onClearAll() }
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(vertical = 35.dp),
            verticalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            items(items = notifications){ (userTriple, textAndTime, eventPair) ->
                val userId = userTriple.first
                val user = userTriple.second
                val uriUserImage = userTriple.third
                val eventId = eventPair.first
                val event = eventPair.second
                NotificationItem(
                    userName = "${user.name} ${user.surname}",
                    uriImage = uriUserImage,
                    text = textAndTime.first,
                    eventName = event.name ?: "",
                    timeStamp = textAndTime.second,
                    isInvite = eventId != "",
                    onAcceptInvite = { onAcceptEventInvite(eventId, userId) },
                    onRejectInvite = { onRejectEventInvite(eventId, userId) },
                    onViewEvent = { onViewEvent(eventId) },
                    onViewUser = { onViewUser(userId) }
                )
            }
        }
    }
}