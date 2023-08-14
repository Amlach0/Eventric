package com.eventric.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eventric.R

@Composable
fun BrandTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    paddingValues: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 5.dp),
    left: @Composable TopBarScope.() -> Unit = {},
    center: @Composable TopBarScope.() -> Unit = {},
    right: @Composable TopBarScope.() -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) { TopBarScopeInstance.left() }
        Row(
            Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) { TopBarScopeInstance.center() }
        Row(verticalAlignment = Alignment.CenterVertically) { TopBarScopeInstance.right() }
    }
}

object TopBarScopeInstance : TopBarScope
interface TopBarScope {
    @Composable
    fun ActionButton(
        @DrawableRes iconId: Int,
        onClick: () -> Unit,
    ) {
        IconButton(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.small
                ),
            onClick = { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .size(23.dp),
                painter = painterResource(iconId),
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }

    @Composable
    fun ExploreActionButton(
        @DrawableRes iconId: Int,
        onClick: () -> Unit,
    ) {
        IconButton(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            onClick = { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(iconId),
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }

    // TODO da mettere apposto
    @Composable
    fun Title(
        modifier: Modifier = Modifier,
        title: String,
        color: Color = MaterialTheme.colors.onPrimary,
        textAlign: TextAlign = TextAlign.Center,
    ) {
        Text(
            modifier = modifier,
            text = title,
            color = color,
            style = MaterialTheme.typography.h4,
            textAlign = textAlign
        )
    }


    @Composable
    fun Back(
        modifier: Modifier = Modifier,
        tint: Color = MaterialTheme.colors.onPrimary,
        navController: NavController,
        onClick: (() -> Unit)? = null,
    ) {
        Image(
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = MaterialTheme.colors.onPrimary,
                        bounded = false
                    )
                ) { if (onClick != null) onClick() else navController.popBackStack() }
                .size(22.dp),
            painter = painterResource(R.drawable.ic_back),
            colorFilter = ColorFilter.tint(tint),
            contentDescription = "action_icon",
        )
    }

}