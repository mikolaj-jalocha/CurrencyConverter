package com.mj.currencyconverter.data.model

data class ConvertedCurrency(
    val from: Currency,
    val to: Currency,
    val rate: Float,
    val fromAmount: Float,
    val toAmount: Float
)
