package com.eventric.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.EventType


@Composable
fun EventTypeItem(
    modifier: Modifier = Modifier,
    type: EventType,
    selected: Boolean = false,
    onClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .clickable { onClick() }
            .background(
                color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 1.dp,
                color = if (!selected) MaterialTheme.colors.onBackground else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp),
            painter = painterResource(type.icon),
            contentDescription = null,
            tint = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = type.title,
            style = MaterialTheme.typography.body1,
            color = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun EventTypeCompactItem(
    type: EventType
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(16.dp),
            painter = painterResource(type.icon),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = type.title,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground
        )
    }
}


@Composable
@Preview
fun EventTypeItemPreview() {

    EventricTheme {
        EventTypeItem(
            type = EventType.InviteOnly,
            selected = false,
            onClick = {}
        )
    }
}
@Composable
@Preview
fun EventTypeCompactItemPreview() {
    EventricTheme {
        EventTypeCompactItem(EventType.Public)
    }
}