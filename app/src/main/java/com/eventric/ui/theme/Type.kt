package com.eventric.ui.theme

import androidx.compose.material.Typography
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
val EventricTypography = Typography(
    body2 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    h6 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    h5 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    h3 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp
    ),
    h2 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 4.sp,
        letterSpacing = 0.sp
    ),
    h1 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 4.sp,
        letterSpacing = 0.sp
    ),
    body1 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    caption = TextStyle(
        fontFamily = airBnbCereal,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)