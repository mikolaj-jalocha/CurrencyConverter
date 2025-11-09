package com.mj.currencyconverter.di

import com.mj.currencyconverter.data.network.CurrencyConversionApi
import com.mj.currencyconverter.data.network.KtorCurrencyConversionApi
import com.mj.currencyconverter.data.repository.CurrencyRepository
import com.mj.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.mj.currencyconverter.ui.CurrencyViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.mj")
class AppModule {

    @Single
    fun httpClient(): HttpClient = HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }
    }

    @Single(binds = [CurrencyConversionApi::class])
    fun ktorDataSource(httpClient: HttpClient) = KtorCurrencyConversionApi(httpClient)

    @Single(binds = [CurrencyRepository::class])
    fun currencyRepository(dataSource: CurrencyConversionApi) =
        CurrencyRepositoryImpl(dataSource)

    @KoinViewModel
    fun currencyConverterViewModel(
        currencyRepository: CurrencyRepository
    ) = CurrencyViewModel(currencyRepository)
}