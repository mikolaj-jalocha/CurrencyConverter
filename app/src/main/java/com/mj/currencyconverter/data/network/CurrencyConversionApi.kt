package com.mj.currencyconverter.data.network

import com.mj.currencyconverter.data.model.ConvertedCurrencyResponse
import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.data.utils.DataError
import com.mj.currencyconverter.data.utils.Result

interface CurrencyConversionApi {
    suspend fun convertCurrencies(
        from: Currency,
        to: Currency,
        amount: Float
    ): Result<ConvertedCurrencyResponse, DataError.Remote>
}