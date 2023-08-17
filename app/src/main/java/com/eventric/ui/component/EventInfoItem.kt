package com.eventric.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.theme.EventricTheme

@Composable
fun EventInfoItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    primaryText: String = "",
    secondaryText: String = "",
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.10F),
                    shape = MaterialTheme.shapes.small
                )
                .padding(9.dp)
                .size(27.dp),
            painter = painterResource(iconId),
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Column(
            modifier = Modifier.padding(start = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (primaryText != "")
                Text(
                    text = primaryText,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSecondary
                )
            if (secondaryText != "")
                Text(
                    text = secondaryText,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground
                )
        }
    }
}


@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
fun EventInfoItemPreview() {
    EventricTheme {
        EventInfoItem(
            iconId = R.drawable.ic_calendar,
            primaryText = "Primary",
            secondaryText = "Secondary"
        )
    }
}