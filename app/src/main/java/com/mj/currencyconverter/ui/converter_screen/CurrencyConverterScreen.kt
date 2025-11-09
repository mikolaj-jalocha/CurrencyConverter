package com.mj.currencyconverter.ui.converter_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mj.currencyconverter.R
import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.ui.theme.RogueRed
import com.mj.currencyconverter.ui.theme.RoyalBlue
import com.mj.currencyconverter.ui.theme.StarWhite
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrencyConverterScreen(
    viewModel: CurrencyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CurrencyConverterScreen(
        onSendingFromValueChange = {
            viewModel.onUpdateSendingFromTextField(it.toFloatOrNull())
        },
        onSendingToCurrencyChange = viewModel::onUpdateSendingToCurrency,
        onSendingFromCurrencyChange = viewModel::onUpdateSendingFromCurrency,
        onSwap = viewModel::swapInputFieldState,
        onConvert = viewModel::convert,
        sendingToCurrency = state.sendingToTextFieldState.currency,
        sendingFromCurrency = state.sendingFromTextFieldState.currency,
        sendingFromValue = state.sendingFromTextFieldState.value.toString(),
        sendingToValue = state.sendingToTextFieldState.value.toString(),
        exchangeRateValue = state.rate,
        maxSendingAmountError = state.sendingFromTextFieldState.isError,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyConverterScreen(
    onSendingFromValueChange: (String) -> Unit,
    onSendingFromCurrencyChange: (Currency) -> Unit,
    onSendingToCurrencyChange: (Currency) -> Unit,
    onConvert: () -> Unit,
    onSwap: () -> Unit,
    sendingFromCurrency: Currency,
    sendingToCurrency: Currency,
    sendingFromValue: String,
    sendingToValue: String,
    exchangeRateValue: Float?,
    maxSendingAmountError: Boolean,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetTitle by remember { mutableIntStateOf(R.string.sending_from) }
    var onCurrencyClick: (Currency) -> Unit = {}

    Scaffold(
        modifier = modifier,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onConvert
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (showBottomSheet) {
                ModalBottomSheet(
                    containerColor = Color.White,
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    CurrenciesListBottomSheetContent(
                        title = stringResource(bottomSheetTitle),
                        onCurrencyClick = {
                            onCurrencyClick(it)
                            scope.launch {
                                sheetState.hide()
                                showBottomSheet = false
                            }
                        },
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = StarWhite
            ) {
                val sendingFromHeightPx = remember { mutableFloatStateOf(0f) }
                val rowHeightPh = remember { mutableFloatStateOf(0f) }

                Column {
                    Box(
                        Modifier.onGloballyPositioned { coords ->
                            sendingFromHeightPx.floatValue = coords.size.height.toFloat()
                        }
                    ) {
                        CurrencyInputField(
                            text = sendingFromValue,
                            onValueChange = onSendingFromValueChange,
                            onSend = onConvert,
                            flagResId = sendingFromCurrency.flag,
                            label = stringResource(R.string.sending_from),
                            currencyCode = sendingFromCurrency.name,
                            onChangeCurrency = {
                                showBottomSheet = true
                                bottomSheetTitle = R.string.sending_from
                                onCurrencyClick = { newCurrency ->
                                    onSendingFromCurrencyChange(newCurrency)
                                }
                            },
                            isError = maxSendingAmountError
                        )
                    }

                    CurrencyInputField(
                        text = sendingToValue,
                        readOnly = true,
                        onValueChange = {},
                        onSend = {},
                        label = stringResource(R.string.receiver_gets),
                        flagResId = sendingToCurrency.flag,
                        currencyCode = sendingToCurrency.name,
                        backgroundColor = StarWhite,
                        textColor = Color.Black,
                        onChangeCurrency = {
                            showBottomSheet = true
                            bottomSheetTitle = R.string.sending_to
                            onCurrencyClick = { newCurrency ->
                                onSendingToCurrencyChange(newCurrency)
                            }
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coords ->
                            rowHeightPh.floatValue = coords.size.height.toFloat()
                        }
                        .offset {
                            val y = sendingFromHeightPx.floatValue - rowHeightPh.floatValue / 2f
                            IntOffset(0, y.coerceAtLeast(0f).toInt())
                        }
                ) {
                    Surface(
                        onClick = onSwap,
                        color = RoyalBlue,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 38.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_swap),
                            modifier = Modifier.padding(8.dp),
                            contentDescription = null,
                        )
                    }

                    if (exchangeRateValue != null) {
                        ExchangeRateText(
                            modifier = Modifier
                                .align(Alignment.Center),
                            exchangeRate = exchangeRateValue,
                            currencyFromSign = sendingFromCurrency.name,
                            currencyToSign = sendingToCurrency.name
                        )
                    }
                }
            }
            AnimatedVisibility(visible = maxSendingAmountError) {
                MaxAmountError(sendingFromCurrency)
            }
        }
    }
}

@Composable
fun MaxAmountError(
    currency: Currency,
    modifier: Modifier = Modifier
) {
    Surface(
        color = RogueRed.copy(alpha = 0.15f),
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_info),
                tint = RogueRed,
                contentDescription = null,
            )
            Text(
                style = MaterialTheme.typography.bodySmall.copy(color = RogueRed),
                text = stringResource(R.string.max_sending_amount) + ": " + currency.maxAmount + " " + currency.name
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ExchangeRateText(
    exchangeRate: Float,
    currencyFromSign: String,
    currencyToSign: String,
    modifier: Modifier = Modifier
) {

    Text(
        text = "1 $currencyFromSign = ${
            String.format("%.2f", exchangeRate).replace(",", ".")
        } $currencyToSign",
        color = Color.White,
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.W700,
            fontSize = 10.sp
        ),
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .padding(vertical = 3.dp, horizontal = 10.dp)
    )
}

@Preview
@Composable
private fun CurrencyConverterScreenPreview() {
    CurrencyConverterScreen(
        onConvert = {},
        onSwap = {},
        onSendingToCurrencyChange = {},
        onSendingFromValueChange = {},
        onSendingFromCurrencyChange = {},
        sendingFromCurrency = Currency.PLN,
        sendingToCurrency = Currency.UAH,
        sendingFromValue = "300.0",
        sendingToValue = "85.27",
        maxSendingAmountError = false,
        exchangeRateValue = 4.24f,
    )
}