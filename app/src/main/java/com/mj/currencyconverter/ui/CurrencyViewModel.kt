package com.mj.currencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.data.repository.CurrencyRepository
import com.mj.currencyconverter.data.utils.onError
import com.mj.currencyconverter.data.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyConverterScreenState())
    val state: StateFlow<CurrencyConverterScreenState> = _state


    init {
        convert()
    }

    fun convert() {
        if (!(state.value.sendingFromTextFieldState.isError)) {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            viewModelScope.launch(Dispatchers.IO) {
                currencyRepository.convertCurrencies(
                    from = state.value.sendingFromTextFieldState.currency,
                    to = state.value.sendingToTextFieldState.currency,
                    amount = state.value.sendingFromTextFieldState.value
                ).onSuccess { convertedCurrency ->
                    _state.update {
                        it.copy(
                            sendingToTextFieldState = it.sendingToTextFieldState.copy(
                                value = convertedCurrency.toAmount
                            ),
                            rate = convertedCurrency.rate,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }.onError { result ->
                    _state.update {
                        it.copy(
                            errorMessage = result.name,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun swapInputFieldState() {
        _state.update { current ->
            val temp = current.sendingFromTextFieldState
            current.copy(
                sendingFromTextFieldState = current.sendingToTextFieldState.copy(shouldValidate = true),
                sendingToTextFieldState = temp
            )
        }
        convert()
    }

    fun onUpdateSendingFromTextField(value: Float?) {
        value?.let {
            _state.update { current ->
                current.copy(
                    sendingFromTextFieldState = current.sendingFromTextFieldState.copy(
                        value = value
                    )
                )
            }
        }
    }

    fun onUpdateSendingFromCurrency(currency: Currency) {
        _state.update { current ->
            current.copy(
                sendingFromTextFieldState = current.sendingFromTextFieldState.copy(
                    currency = currency
                )
            )
        }
        convert()
    }

    fun onUpdateSendingToCurrency(currency: Currency) {
        _state.update { current ->
            current.copy(
                sendingToTextFieldState = current.sendingToTextFieldState.copy(
                    currency = currency
                )
            )
        }
        convert()
    }
}

data class CurrencyConverterScreenState(
    val sendingFromTextFieldState: CurrencyTextFieldState = CurrencyTextFieldState(
        currency = Currency.PLN,
        value = 300f
    ),
    val sendingToTextFieldState: CurrencyTextFieldState = CurrencyTextFieldState(
        currency = Currency.UAH,
        shouldValidate = false
    ),
    val rate: Float? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CurrencyTextFieldState(
    val value: Float = 0f,
    val currency: Currency,
    private val shouldValidate: Boolean = true,
) {
    val isError: Boolean
        get() = if (shouldValidate) value > currency.maxAmount else false
}