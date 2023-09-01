package com.eventric.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.EventCategory


@Composable
fun EventCategoryItem(
    modifier: Modifier = Modifier,
    category: EventCategory,
    selected: Boolean = false,
    showLabel: Boolean = true,
    onClick: () -> Unit = {},
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            modifier = Modifier
                .shadow(
                    elevation = 20.dp,
                    shape = CircleShape,
                    clip = true,
                    spotColor = if (selected) category.color else Color.Transparent
                )
                .background(
                    color = if (selected) category.color else MaterialTheme.colors.background,
                    shape = CircleShape
                )
                .border(
                    width = 0.8.dp,
                    color = if (!selected) MaterialTheme.colors.onBackground else Color.Transparent,
                    shape = CircleShape
                ),
            onClick = { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .padding(15.dp)
                    .size(30.dp),
                painter = painterResource(category.icon),
                contentDescription = null,
                tint = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
            )

        }
        if (showLabel)
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = category.title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
    }
}

@Composable
fun EventCategoryCompactItem(
    category: EventCategory,
    selected: Boolean = false,
    onlyIcon: Boolean = false,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = Modifier
            .shadow(
                elevation = 20.dp,
                shape = CircleShape,
                clip = true,
                spotColor = if (selected) category.color else Color.Transparent
            ),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) category.color else Color.Transparent,
        ),
        border = BorderStroke(
            width = 0.8.dp,
            color = if (!selected) MaterialTheme.colors.onBackground else Color.Transparent,
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp, pressedElevation = 0.dp
        ),
        onClick = { onClick() }
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp),
            painter = painterResource(category.icon),
            contentDescription = null,
            tint = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
        )
        if (!onlyIcon)
            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                text = category.title,
                style = MaterialTheme.typography.body2,
                color = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
            )
    }
}

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    category: EventCategory,
) {
    Icon(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(7.dp)
            )
            .padding(5.dp),
        painter = painterResource(category.icon),
        contentDescription = null,
        tint = category.color
    )
}

@Composable
fun CategoryCompactIcon(
    modifier: Modifier = Modifier,
    category: EventCategory,
) {
    Icon(
        modifier = modifier
            .size(20.dp),
        painter = painterResource(category.icon),
        contentDescription = null,
        tint = category.color
    )
}

@Composable
@Preview
fun EventCategoryItemUnselectedPreview() {
    EventricTheme {
        EventCategoryItem(
            category = EventCategory.Art,
            selected = false,
            onClick = {}
        )
    }
}

@Composable
@Preview
fun EventCategoryItemSelectedPreview() {
    EventricTheme {
        EventCategoryItem(
            category = EventCategory.Art,
            selected = true,
            showLabel = false,
            onClick = {}
        )
    }
}

@Composable
@Preview
fun EventCategoryCompactItemPreview() {
    EventricTheme {
        EventCategoryCompactItem(
            category = EventCategory.Art,
            selected = false,
            onlyIcon = false
        ) {}
    }
}

@Composable
@Preview
fun CategoryIconPreview() {
    EventricTheme {
        CategoryIcon(
            category = EventCategory.Art,
        )
    }
}

@Composable
@Preview
fun CategoryCompactIconPreview() {
    EventricTheme {
        CategoryCompactIcon(
            category = EventCategory.Art,
        )
    }
}