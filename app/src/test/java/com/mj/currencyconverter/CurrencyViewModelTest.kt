package com.mj.currencyconverter

import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.data.repository.CurrencyRepository
import com.mj.currencyconverter.ui.converter_screen.CurrencyViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

class CurrencyViewModelTest {

    @Test
    fun `swapInputFieldState swaps currencies and values`() {
        val repo = mock(CurrencyRepository::class.java)
        val viewModel = CurrencyViewModel(repo)

        val beforeFrom = viewModel.state.value.sendingFromTextFieldState
        val beforeTo = viewModel.state.value.sendingToTextFieldState

        viewModel.swapInputFieldState()

        val afterFrom = viewModel.state.value.sendingFromTextFieldState
        val afterTo = viewModel.state.value.sendingToTextFieldState

        assertEquals(beforeTo.currency, afterFrom.currency)
        assertEquals(beforeFrom.currency, afterTo.currency)

        assertEquals(beforeTo.value, afterFrom.value, 0.0001f)
        assertEquals(beforeFrom.value, afterTo.value, 0.0001f)
    }


    @Test
    fun `onUpdateSendingFromTextField updates value and recalculates error`() {

        val repo = mock(CurrencyRepository::class.java)
        val viewModel = CurrencyViewModel(repo)
        val currency = viewModel.state.value.sendingFromTextFieldState.currency
        val max = currency.maxAmount

        viewModel.onUpdateSendingFromTextField(max + 0.01f)
        assertTrue(viewModel.state.value.sendingFromTextFieldState.isError)
        assertEquals(max + 0.01f, viewModel.state.value.sendingFromTextFieldState.value, 0.0001f)

        viewModel.onUpdateSendingFromTextField(max)
        assertFalse(viewModel.state.value.sendingFromTextFieldState.isError)
        assertEquals(max, viewModel.state.value.sendingFromTextFieldState.value, 0.0001f)

        viewModel.onUpdateSendingFromTextField(max - 1f)
        assertFalse(viewModel.state.value.sendingFromTextFieldState.isError)
        assertEquals(max - 1f, viewModel.state.value.sendingFromTextFieldState.value, 0.0001f)
    }

    @Test
    fun `onUpdateSendingFromCurrency updates currency immediately`() {

        val repo = mock(CurrencyRepository::class.java)
        val viewModel = CurrencyViewModel(repo)
        val current = viewModel.state.value.sendingFromTextFieldState.currency
        val newCurrency = Currency.entries.first { it != current }


        viewModel.onUpdateSendingFromCurrency(newCurrency)

        assertEquals(newCurrency, viewModel.state.value.sendingFromTextFieldState.currency)
    }

    @Test
    fun `onUpdateSendingToCurrency updates currency immediately`() {

        val repo = mock(CurrencyRepository::class.java)
        val viewModel = CurrencyViewModel(repo)
        val current = viewModel.state.value.sendingToTextFieldState.currency
        val newCurrency = Currency.entries.first { it != current }

        viewModel.onUpdateSendingToCurrency(newCurrency)

        assertEquals(newCurrency, viewModel.state.value.sendingToTextFieldState.currency)
    }
}