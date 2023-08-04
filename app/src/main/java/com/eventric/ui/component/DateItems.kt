package com.eventric.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eventric.ui.theme.EventricTheme
import java.text.DateFormatSymbols
import java.util.Locale


@Composable
fun DateCompactItem(
    modifier: Modifier = Modifier,
    startDate: String,
    endDate: String,
) {
    val startDateSplit = startDate.split("/")
    val startDay = startDateSplit[0]
    val startMonth = getMonth(startDateSplit[1].toInt())?.uppercase(Locale.ROOT)

    val endDateSplit = endDate.split("/")
    val endDay = endDateSplit[0]
    val endMonth = getMonth(endDateSplit[1].toInt())?.uppercase(Locale.ROOT)
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.small
            )
            .padding(4.dp)
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(MaterialTheme.typography.h2.toSpanStyle()) {
                    append("$startDay\n")
                }
                withStyle(MaterialTheme.typography.subtitle2.toSpanStyle()) {
                    append("$startMonth")
                }
            },
            lineHeight = 13.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )

        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = "-",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = buildAnnotatedString {
                withStyle(MaterialTheme.typography.h2.toSpanStyle()) {
                    append(endDay)
                }
                withStyle(MaterialTheme.typography.subtitle2.toSpanStyle()) {
                    append("\n$endMonth")
                }
            },
            lineHeight = 13.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )
    }

}


@Composable
@Preview
fun DateCompactItemPreview() {
    EventricTheme {
        DateCompactItem(
            startDate = "10/09/1000",
            endDate = "09/11/2000"
        )
    }
}


fun getMonth(month: Int): String? = DateFormatSymbols().shortMonths[month - 1]