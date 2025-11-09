package com.mj.currencyconverter.data.model

import androidx.annotation.DrawableRes
import com.mj.currencyconverter.R

enum class Currency(
    val maxAmount: Float,
    @DrawableRes val flag: Int,
    val country: String,
    val fullName: String
) {
    PLN(20000f, R.drawable.ic_pln, "Poland", "Polish zloty"),
    EUR(5000f, R.drawable.ice_eur, "Germany", "Euro"),
    GBP(1000f, R.drawable.ic_gbp, "Great Britain", "British Pound"),
    UAH(50000f, R.drawable.ic_uah, "Ukraine", "Hrivna")
}