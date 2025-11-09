package com.mj.currencyconverter.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mj.currencyconverter.R
import com.mj.currencyconverter.data.model.Currency
import com.mj.currencyconverter.ui.theme.AluminiumGray
import com.mj.currencyconverter.ui.theme.SmokyGray
import com.mj.currencyconverter.ui.theme.StarWhite

@Composable
fun CurrenciesListBottomSheetContent(
    title: String,
    onCurrencyClick: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    val queryState = rememberSaveable { mutableStateOf("") }

    val filteredCurrencies = remember(queryState.value) {
        filterAndSortCurrencies(queryState.value)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxHeight(0.85f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 26.sp, fontWeight = FontWeight.W700)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = queryState.value,
            onValueChange = { queryState.value = it },
            label = {
                Text(stringResource(R.string.search), style = MaterialTheme.typography.bodySmall)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    modifier = Modifier.size(18.dp),
                    tint = AluminiumGray,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = AluminiumGray,
                focusedBorderColor = AluminiumGray,
                unfocusedBorderColor = AluminiumGray,
                disabledBorderColor = AluminiumGray,
                errorBorderColor = AluminiumGray
            )
        )
        Spacer(Modifier.height(16.dp))
        Text(
            stringResource(R.string.all_countries),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(Modifier.height(16.dp))

        filteredCurrencies.forEach { currency ->
            CurrencyTile(
                currency = currency,
                onClick = { onCurrencyClick(currency) }
            )
            HorizontalDivider(color = StarWhite, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun CurrencyTile(
    currency: Currency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable(enabled = true, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(0.15f)) {
            Box(
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(StarWhite),
                contentAlignment = Alignment.Center
            ) {
                Image(painterResource(currency.flag), contentDescription = null)
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.75f)
                .fillMaxWidth()
        ) {
            Text(
                text = currency.country,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = currency.fullName + " • " + currency.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = W400),
                color = SmokyGray
            )

        }
    }
}


fun filterAndSortCurrencies(query: String): List<Currency> {
    val q = query.trim().lowercase()
    return Currency.entries
        .map { currency ->
            val lowerName = currency.name.lowercase()
            val lowerFull = currency.fullName.lowercase()
            val lowerCountry = currency.country.lowercase()
            val score = when {
                q.isEmpty() -> 0
                lowerName == q || lowerFull == q || lowerCountry == q -> 100
                lowerName.startsWith(q) || lowerFull.startsWith(q) || lowerCountry.startsWith(q) -> 50
                lowerName.contains(q) || lowerFull.contains(q) || lowerCountry.contains(q) -> 10
                else -> 0
            }
            currency to score
        }
        .sortedWith(
            compareByDescending<Pair<Currency, Int>> { it.second }
                .thenBy { it.first.country }
        )
        .map { it.first }
}

@Preview(
    showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 360, heightDp = 800
)
@Composable
private fun CurrenciesListBottomSheetContentPreview() {
    CurrenciesListBottomSheetContent("Sending to", {})
}