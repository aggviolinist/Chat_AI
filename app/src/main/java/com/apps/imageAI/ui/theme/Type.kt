package com.apps.imageAI.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apps.imageAI.R
import com.apps.imageAI.R.font.labrada_font_family


val Labrada = FontFamily(
    Font(labrada_font_family)
)
val Montserrat = FontFamily(
    Font(R.font.clarendon_regular, FontWeight.SemiBold)
)
// Set of Material typography styles to start with

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.W400,
        fontSize = 16.5.sp,
        lineHeight = 25.sp,
        color = Surface
    ),

    bodySmall = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.W400,
        color = Surface
    ),

    displayLarge = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),

    displayMedium = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),

    displaySmall = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Labrada,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)