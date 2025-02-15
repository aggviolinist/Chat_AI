package com.apps.aivision.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apps.aivision.R

val Barlow = FontFamily(
    Font(R.font.barlow_font_family)
)
val Montserrat = FontFamily(
    Font(R.font.varela_round, FontWeight.Medium)
)
// Set of Material typography styles to start with

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 25.sp,
        color = Surface
    ),

    bodySmall = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.W400,
        color = Surface
    ),

    displayLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),

    displayMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),

    displaySmall = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Barlow,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)