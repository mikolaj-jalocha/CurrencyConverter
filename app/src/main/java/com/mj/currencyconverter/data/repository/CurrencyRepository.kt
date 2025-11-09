package com.mj.currencyconverter.data.repository

import com.mj.currencyconverter.data.model.ConvertedCurrency
import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.data.utils.DataError
import com.mj.currencyconverter.data.utils.Result

interface CurrencyRepository {
    suspend fun convertCurrencies(
        from: Currency,
        to: Currency,
        amount: Float
    ): Result<ConvertedCurrency, DataError.Remote>
}