package com.eventric.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.vo.EventCategory


@Composable
fun EventCategoryItem(
    category: EventCategory,
    selected: Boolean = false,
    onClick: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            modifier = Modifier
                .background(
                    color = if (selected) category.color else Color.Transparent,
                    shape = CircleShape
                )
                .border(
                    width = 0.8.dp,
                    color = if (!selected) MaterialTheme.colors.onBackground else Color.Transparent,
                    shape = CircleShape
                )
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    ambientColor = category.color,
                    spotColor = MaterialTheme.colors.primary
                ),
            onClick = { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .padding(15.dp),
                painter = painterResource(category.icon),
                contentDescription = null,
                tint = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
            )

        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = category.title,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}