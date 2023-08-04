package com.eventric.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.theme.EventricTheme


@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(7.dp)
            )
            .padding(0.dp),
        onClick = { onClick() }
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(if (isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_void),
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
) {
    Icon(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(7.dp)
            )
            .padding(8.dp),
        painter = painterResource(if (isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_void),
        contentDescription = null,
        tint = MaterialTheme.colors.primary
    )

}


@Composable
@Preview
fun FavoriteIconPreview() {
    EventricTheme {
        FavoriteIcon(
            true
        )
    }
}