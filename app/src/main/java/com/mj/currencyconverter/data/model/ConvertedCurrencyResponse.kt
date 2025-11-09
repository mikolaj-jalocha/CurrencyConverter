package com.mj.currencyconverter.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ConvertedCurrencyResponse(
    val from: Currency,
    val to: Currency,
    val rate: Float,
    val fromAmount: Float,
    val toAmount: Float
)

fun ConvertedCurrencyResponse.toConvertedCurrency() =
    ConvertedCurrency(
        from = from,
        to = to,
        rate = rate,
        fromAmount = fromAmount,
        toAmount = toAmount
    )
