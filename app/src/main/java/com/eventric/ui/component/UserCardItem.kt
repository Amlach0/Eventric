package com.eventric.ui.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
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
import coil.compose.AsyncImage
import com.eventric.R
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.User


@Composable
fun UserCardCompactItem(
    modifier: Modifier = Modifier,
    user: User,
    isFollowed: Boolean,
    uriImage: Uri,
    onClick: () -> Unit,
    onFollowClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 7.dp, horizontal = 9.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(44.dp)
                    .clip(MaterialTheme.shapes.small),
                model = if (uriImage == Uri.EMPTY) R.drawable.img_profile_placeholder else uriImage,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_profile_placeholder),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 17.dp),
                text = user.name.toString() + " " + user.surname.toString(),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSecondary
            )
            CustomButtonTertiary(
                text = stringResource(if (isFollowed) R.string.unfollow_label else R.string.follow_label),
                onClick = { onFollowClick() }
            )

        }
    }
}

@Composable
fun UserCardCompactEmptyItem(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.empty_users_label),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}


@Preview
@Composable
fun UserCardCompactItemPreview() {
    EventricTheme {
        UserCardCompactItem(
            user = User().copy(name = "Antonio"),
            isFollowed = true,
            uriImage = Uri.EMPTY,
            onClick = {}) {

        }
    }
}