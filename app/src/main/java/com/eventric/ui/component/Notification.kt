package com.eventric.ui.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eventric.R
import com.eventric.ui.theme.EventricTheme

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    userName: String,
    uriImage: Uri,
    text: String,
    eventName: String,
    timeStamp: String,
    isInvite: Boolean,
    onAcceptInvite: () -> Unit,
    onRejectInvite: () -> Unit,
    onViewEvent: () -> Unit,
    onViewUser: () -> Unit,
) {
    Column {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable { onViewUser() },
                model = if (uriImage== Uri.EMPTY) R.drawable.img_profile_placeholder else uriImage,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_profile_placeholder),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp),
                text = buildAnnotatedString {
                    withStyle(MaterialTheme.typography.h5.toSpanStyle().copy(fontSize = 14.sp)) {
                        append(userName)
                    }
                    withStyle(MaterialTheme.typography.body1.toSpanStyle()) {
                        append(text)
                    }
                    withStyle(MaterialTheme.typography.subtitle1.toSpanStyle()) {
                        append(eventName)
                    }
                },
                color = MaterialTheme.colors.onSecondary
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = timeStamp.replace(" ", "\n"),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Right,
                color = MaterialTheme.colors.onBackground
            )
        }

        if (isInvite) {
            Row(
                modifier = Modifier.padding(start = 65.dp, top = 10.dp),
            ) {
                CustomButtonSecondary(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colors.onBackground,
                    text = stringResource(R.string.reject_label),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { onRejectInvite() }
                )
                CustomButtonPrimary(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    text = stringResource(R.string.accept_label),
                    showArrowIcon = false,
                    contentPadding = PaddingValues(0.dp),
                    onClick = { onAcceptInvite() }
                )
                CustomButtonSecondary(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    text = stringResource(R.string.view_event_label),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { onViewEvent() }
                )
            }
        }
    }

}




@Preview
@Composable
fun NotificationItemPreview() {
    EventricTheme {
        NotificationItem(
            userName = "Mario Dragoni",
            uriImage = Uri.EMPTY,
            text = " ti ha invitato a ",
            eventName = "Festa dell'anno",
            timeStamp = "10/07/2023 02:00",
            isInvite = true,
            onAcceptInvite = { /*TODO*/ },
            onRejectInvite = { /*TODO*/ },
            onViewEvent = { /*TODO*/ },
            onViewUser = { /*TODO*/ },
        )
    }
}