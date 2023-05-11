package com.eventric.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eventric.R

val airBnbCereal = FontFamily(
    Font(R.font.airbnb_cereal_medium, FontWeight.Medium),
    Font(R.font.airbnb_cereal_bold, FontWeight.Bold),
    Font(R.font.airbnb_cereal_light, FontWeight.Light),
    Font(R.font.airbnb_cereal_black, FontWeight.Black),
    Font(R.font.airbnb_cereal_extra_bold, FontWeight.ExtraBold),
    Font(R.font.airbnb_cereal_book, FontWeight.Normal) // Set on Normal because Book doesn't exist
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)