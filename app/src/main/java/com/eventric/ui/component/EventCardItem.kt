package com.eventric.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.Event


@Composable
fun EventCardItem(
    modifier: Modifier = Modifier,
    event: Event,
    organiserName: String,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                    painter = painterResource(R.drawable.img_event),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DateCompactItem(
                        startDate = event.date?.start ?: "",
                        endDate = event.date?.end ?: ""
                    )
                    FavoriteIcon(isFavorite)
                }
            }

            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .padding(top = 9.dp)
            ) {
                Text(
                    text = event.name.toString(),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onSecondary
                )
                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfileCompactItem(
                        name = organiserName,
                        imageId = R.drawable.img_profile
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    ProfileGoingItem(numberOfGoing = event.subscribed.size)
                }
                LocationCompactItem(location = event.location ?: "")
            }
        }
    }
}

@Composable
fun EventCardCompactItem(
    modifier: Modifier = Modifier,
    event: Event,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(106.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 9.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                painter = painterResource(R.drawable.img_event),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp),
            ) {
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${event.date?.start} - ${event.date?.end}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.primary
                    )
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource( if (isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_void ),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    text = event.name.toString(),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSecondary
                )
                LocationCompactItem(location = event.location ?: "")
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun EventCardItemPreview() {
    EventricTheme {
        EventCardItem(
            event = Event(),
            organiserName = "Carlo Santini",
            isFavorite = true,
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun EventCardCompactItemPreview() {
    EventricTheme {
        EventCardCompactItem(
            event = Event(),
            isFavorite = true,
            onClick = {}
        )
    }
}