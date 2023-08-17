package com.eventric.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.theme.EventricTheme

@Composable
fun ProfileCompactItem(
    name: String,
    @DrawableRes imageId: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(30.dp)
                .clip(MaterialTheme.shapes.small),
            painter = painterResource(imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(start = 11.dp),
            text = name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
fun ProfileOrganizerItem(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes imageId: Int,
    isFollowed: Boolean,
    showFollowButton: Boolean = true,
    onFollowClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(44.dp)
                .clip(MaterialTheme.shapes.small),
            painter = painterResource(imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary
            )
            Text(
                text = stringResource(R.string.organizer_label),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
        if (showFollowButton)
            CustomButtonTertiary(
                text = stringResource(if (isFollowed) R.string.unfollow_label else R.string.follow_label),
                onClick = { onFollowClick() }
            )
    }
}

@Composable
fun ProfileGoingItem(
    numberOfGoing: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(R.drawable.ic_type_public),
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "$numberOfGoing Going",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes imageId: Int,
    isInvited: Boolean,
    showInviteButton: Boolean = false,
    onInviteClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            painter = painterResource(imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp),
            text = name,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSecondary
        )
        if (showInviteButton)
            CustomButtonTertiary(
                text = stringResource(if (isInvited) R.string.uninvite_label else R.string.invite_label),
                onClick = { onInviteClick() }
            )
    }
}

@Composable
fun ProfileGoingExpandedItem(
    modifier: Modifier = Modifier,
    numberOfGoing: Int,
    showInviteButton: Boolean = true,
    onClick: () -> Unit,
    onInviteClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = CircleShape,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                painter = painterResource(id = R.drawable.ic_type_public),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "$numberOfGoing Going",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )

            if (showInviteButton)
                Button(
                    modifier = Modifier.padding(start = 27.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(7.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    onClick = { onInviteClick() },
                ) {
                    Text(
                        text = stringResource(R.string.invite_btn_label),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
        }
    }

}


@Preview
@Composable
fun ProfileExpandedItemPreview() {
    EventricTheme {
        ProfileOrganizerItem(
            name = "Antonio Cagliari",
            imageId = R.drawable.img_profile,
            isFollowed = false,
            showFollowButton = true,
            onFollowClick = {},
        )
    }
}

@Preview
@Composable
fun ProfileItemPreview() {
    EventricTheme {
        ProfileItem(
            name = "Antonio Cagliari",
            imageId = R.drawable.img_profile,
            isInvited = false,
            showInviteButton = true,
            onInviteClick = {},
        )
    }
}

@Preview
@Composable
fun ProfileGoingExpandedItemPreview() {
    EventricTheme {
        ProfileGoingExpandedItem(
            numberOfGoing = 20,
            showInviteButton = true,
            onClick = {},
            onInviteClick = {},
        )
    }
}

@Composable
@Preview
fun ProfileGoingItemPreview() {
    EventricTheme {
        ProfileGoingItem(20)
    }
}
