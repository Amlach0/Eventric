package com.eventric.ui.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.component.BrandTopBar

@Composable
fun ExploreContent(

) {
    Text(text = "Questa è explore")
}

@Composable
fun ExploreTopBar(
    address: String,
    goToMap: () -> Unit,
    goToNotification: () -> Unit,
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
            ActionButton(
                iconId = R.drawable.ic_map,
                onClick = { goToMap() }
            )
        },
        center = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(end = 1.dp),
                        text = "Current Location",
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_expand),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Text(
                    text = address,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        },
        right = {
            ActionButton(
                iconId = R.drawable.ic_notification,
                onClick = { goToNotification() }
            )
        }
    )
}

