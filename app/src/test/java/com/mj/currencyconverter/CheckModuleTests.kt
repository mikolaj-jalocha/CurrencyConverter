package com.mj.currencyconverter

import com.mj.currencyconverter.di.AppModule
import org.junit.Test
import org.koin.android.test.verify.androidVerify
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.ksp.generated.module
import org.koin.test.KoinTest

class CheckModuleTests : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules(){
        AppModule().module.androidVerify()
    }
}