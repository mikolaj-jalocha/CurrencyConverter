package com.mj.currencyconverter

import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.ui.filterAndSortCurrencies
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CurrenciesListSearchTest {

    @Test
    fun `empty query returns all sorted by country`() {
        val result = filterAndSortCurrencies("")
        assertEquals(Currency.entries.size, result.size)
        val expected = Currency.entries.sortedBy { it.country }
        assertEquals(expected, result)
    }

    @Test
    fun `exact match appears first`() {
        val target = Currency.entries.first()
        val query = target.name
        val result = filterAndSortCurrencies(query)
        assertTrue(result.isNotEmpty())
        assertEquals(target, result.first())
    }
}