package com.eventric.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColors = darkColors(
    primary = Purple,
    onPrimary = White,
    secondary = LightPurple,
    background = Black,
    onBackground = LightGray,
    error = Red
)

private val LightColors = lightColors(
    primary = Purple,
    onPrimary = White,
    secondary = LightPurple,
    onSecondary = Black,
    background = White,
    onBackground = LightGray,
    surface = White70,
    error = Red,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EventricTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = EventricTypography,
        shapes = EventricShapes,
        content = content
    )
}