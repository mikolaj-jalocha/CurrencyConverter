package com.mj.currencyconverter.data.repository

import com.mj.currencyconverter.data.model.ConvertedCurrency
import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.data.model.toConvertedCurrency
import com.mj.currencyconverter.data.network.CurrencyConversionApi
import com.mj.currencyconverter.data.utils.DataError
import com.mj.currencyconverter.data.utils.Result
import com.mj.currencyconverter.data.utils.map

class CurrencyRepositoryImpl(
    private val dataSource: CurrencyConversionApi
) : CurrencyRepository {
    override suspend fun convertCurrencies(
        from: Currency,
        to: Currency,
        amount: Float
    ): Result<ConvertedCurrency, DataError.Remote> {
        return dataSource.convertCurrencies(from, to, amount).map {
            it.toConvertedCurrency()
        }
    }
}