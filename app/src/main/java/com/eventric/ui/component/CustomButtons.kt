package com.eventric.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.R

@Composable
fun CustomButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconId: Int? = null,
    showArrowIcon: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(12.dp),
        onClick = { onClick() },
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (iconId != null)
                Icon(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(22.dp),
                    painter = painterResource(iconId),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary,
            )
        }
        if (showArrowIcon)
            Icon(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = CircleShape
                    )
                    .padding(6.dp),
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
    }
}

@Composable
fun CustomButtonSecondary(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconId: Int? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        border = BorderStroke(1.5.dp, MaterialTheme.colors.primary),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(12.dp),
        onClick = { onClick() },
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (iconId != null)
                Icon(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(22.dp),
                    painter = painterResource(iconId),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary,
            )
        }
    }

}

@Composable
fun CustomButtonSelector(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconId: Int? = null,
    onClick: () -> Unit,
) {
    Column(modifier) {
        OutlinedButton(
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
            contentPadding = PaddingValues(9.dp),
            onClick = { onClick() },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (iconId != null)
                    Icon(
                        modifier = Modifier
                            .padding(end = 13.dp)
                            .size(22.dp),
                        painter = painterResource(iconId),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                Text(
                    text = text,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 13.dp)
                        .size(20.dp),
                    painter = painterResource(R.drawable.ic_keyboard_arrow),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}


@Composable
@Preview(heightDp = 60)
fun CustomButtonSubmitPreview() {
    CustomButtonPrimary(
        modifier = Modifier,
        text = "Test"
    ) {

    }
}

@Composable
@Preview(heightDp = 60)
fun CustomButtonSecondaryPreview() {
    CustomButtonSecondary(
        modifier = Modifier,
        text = "Test"
    ) {

    }
}

@Composable
@Preview(heightDp = 60)
fun CustomButtonSelectorPreview() {
    CustomButtonSelector(
        modifier = Modifier,
        text = "Long test test Test",
        iconId = R.drawable.ic_calendar
    ) {

    }
}