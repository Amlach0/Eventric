package com.eventric.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import com.eventric.R
import com.eventric.ui.theme.EventricTheme

@Composable
fun BrandSelector(
    modifier: Modifier = Modifier,
    dataList: List<SelectorItemData>,
    selectedItem: SelectorItemData,
    onChangeSelectedItem: (SelectorItemData) -> Unit,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .padding( horizontal = 5.dp ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        for(itemData in dataList) {
            BrandSelectorItem(
                modifier = Modifier.weight(1f),
                data = itemData,
                isSelected = selectedItem == itemData,
                onSelected = { onChangeSelectedItem(itemData) }
            )
        }
    }
}

@Composable
fun BrandSelectorItem(
    modifier: Modifier = Modifier,
    data: SelectorItemData,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(
            defaultElevation = if (isSelected) 5.dp else 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.onPrimary else Color.Transparent
        ),
        onClick = { onSelected() }
    ) {
        if (data.iconId != null)
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 5.dp),
                painter = painterResource(data.iconId),
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
            )
        Text(
            text = data.label,
            style = MaterialTheme.typography.body2,
            color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
        )
    }
}

data class SelectorItemData(
    val value: String,
    val label: String,
    val iconId: Int?,
)


@Preview
@Composable
fun BrandSelectorPreview() {
    val dataList = listOf(
        SelectorItemData(
            value = "a",
            label = "Preferiti",
            iconId = R.drawable.ic_favorite_fill
        ),
        SelectorItemData(
            value = "b",
            label = "Pref",
            iconId = R.drawable.ic_favorite_fill
        )
    )
    EventricTheme {
        BrandSelector(
            modifier = Modifier,
            dataList = dataList,
            selectedItem = dataList[0],
            onChangeSelectedItem = {}
        )
    }
}

@Preview
@Composable
fun BrandSelectorItemPreview() {
    EventricTheme {
        BrandSelectorItem(
            data = SelectorItemData(
                value = "a",
                label = "Preferiti",
                iconId = R.drawable.ic_favorite_fill
            ),
            isSelected = true,
            onSelected = {}
        )
    }
}