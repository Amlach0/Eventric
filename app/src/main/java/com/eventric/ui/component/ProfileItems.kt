package com.eventric.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
@Preview
fun ProfileItemPreview() {
    EventricTheme {
        ProfileCompactItem(name = "Carlo Santini", imageId = R.drawable.img_profile)
    }
}

@Composable
@Preview
fun ProfileGoingItemPreview() {
    EventricTheme {
        ProfileGoingItem(20)
    }
}