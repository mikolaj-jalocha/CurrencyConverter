package com.mj.currencyconverter.data.network

import com.mj.currencyconverter.data.model.ConvertedCurrencyResponse
import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.data.utils.DataError
import com.mj.currencyconverter.data.utils.Result
import com.mj.currencyconverter.data.utils.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class KtorCurrencyConversionApi(
    private val httpClient: HttpClient
) : CurrencyConversionApi {
    override suspend fun convertCurrencies(
        from: Currency,
        to: Currency,
        amount: Float
    ): Result<ConvertedCurrencyResponse, DataError.Remote> {
        return safeCall<ConvertedCurrencyResponse> {
            httpClient.get {
                url("https://my.transfergo.com/api/fx-rates")
                parameter("from", from.name)
                parameter("to", to.name)
                parameter("amount", amount)
            }
        }
    }
}