package com.mj.currencyconverter.data.utils

import com.mj.currencyconverter.data.utils.Error

sealed interface DataError: Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }
}