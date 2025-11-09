package com.mj.currencyconverter.ui.converter_screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mj.currencyconverter.R
import com.mj.currencyconverter.ui.theme.RadicalRed
import com.mj.currencyconverter.ui.theme.RoyalBlue
import com.mj.currencyconverter.ui.theme.SmokyGray

@Composable
fun CurrencyInputField(
    text: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onChangeCurrency: () -> Unit,
    @DrawableRes
    flagResId: Int,
    currencyCode: String,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    isError: Boolean = false,
    backgroundColor: Color = Color.White,
    textColor: Color = RoyalBlue,
) {
    Surface(
        modifier = modifier
            .wrapContentSize()
            .then(
                if (isError)
                    Modifier.border(2.dp, RadicalRed, RoundedCornerShape(20.dp))
                else
                    Modifier
            ),
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onChangeCurrency()
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = W400),
                    color = SmokyGray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(flagResId),
                        contentDescription = null,
                    )
                    Text(
                        text = currencyCode,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp, end = 4.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_down),
                        contentDescription = null,
                        tint = SmokyGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            CompositionLocalProvider(
                LocalTextSelectionColors provides
                        TextSelectionColors(
                            handleColor = RoyalBlue,
                            backgroundColor = RoyalBlue.copy(alpha = 0.4f)
                        )
            ) {
                BasicTextField(
                    readOnly = readOnly,
                    value = text,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                    ),
                    cursorBrush = SolidColor(RoyalBlue),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            onSend()
                        }),
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        color = if (isError) RadicalRed else textColor,
                        letterSpacing = TextUnit(-1.25f, TextUnitType.Sp),
                        textAlign = TextAlign.End
                    )
                )
            }
        }
    }
}

@Preview(
    showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 360, heightDp = 800
)
@Composable
private fun CurrencyInputFieldPreview() {
    CurrencyInputField(
        text = "300.00",
        onValueChange = {},
        onSend = {},
        flagResId = R.drawable.ic_pln,
        currencyCode = "PLN",
        label = "Sending from",
        onChangeCurrency = {}
    )
}