package com.mj.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mj.currencyconverter.di.AppModule
import com.mj.currencyconverter.ui.converter_screen.CurrencyConverterScreen
import com.mj.currencyconverter.ui.theme.CurrencyConverterTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(AppModule().module)
        }
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                CurrencyConverterScreen()
            }
        }
    }
}