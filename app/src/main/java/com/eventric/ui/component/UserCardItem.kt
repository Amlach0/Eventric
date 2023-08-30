package com.eventric.ui.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eventric.R
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
            .height(70.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 9.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(44.dp)
                    .clip(MaterialTheme.shapes.small),
                model = if (uriImage== Uri.EMPTY) R.drawable.img_profile_placeholder else uriImage,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_profile_placeholder),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    text = user.name.toString()+" "+user.surname.toString(),
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