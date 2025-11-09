package com.mj.currencyconverter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mj.currencyconverter.R

val interFamily = FontFamily(
    Font(R.font.inter_black, FontWeight.Black),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal),
)
val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.W700,
        fontSize = 26.sp
    ),

    bodySmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.W700,
        fontSize = 34.sp
    ),
)

