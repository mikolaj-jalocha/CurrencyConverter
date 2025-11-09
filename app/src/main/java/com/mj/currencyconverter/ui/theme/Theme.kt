package com.mj.currencyconverter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable


@Composable
fun CurrencyConverterTheme(
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}