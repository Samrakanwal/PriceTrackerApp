package com.example.pricetrackerapp.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocktracker.data.model.PriceChangeDirection
import com.example.stocktracker.data.model.StockPrice
import com.example.stocktracker.ui.feed.FeedViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    onClick: (String) -> Unit,
    vm: FeedViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (state.connected) "🟢 Connected" else "🔴 Disconnected")
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (state.connected) vm.stop() else vm.start()
                        }
                    ) {
                        Text(if (state.connected) "Stop" else "Start")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(
                items = state.stocks,
                key = { it.symbol }
            ) { stock ->
                StockRow(stock = stock, onClick = onClick)
            }
        }
    }
}

@Composable
private fun StockRow(
    stock: StockPrice,
    onClick: (String) -> Unit
) {
    var flashDirection by remember(stock.symbol) {
        mutableStateOf(PriceChangeDirection.NONE)
    }

    LaunchedEffect(stock.price, stock.previousPrice) {
        if (stock.price != stock.previousPrice) {
            flashDirection = stock.changeDirection
            delay(1000)
            flashDirection = PriceChangeDirection.NONE
        }
    }

    val targetColor = when (flashDirection) {
        PriceChangeDirection.UP -> Color(0x332ECC71)
        PriceChangeDirection.DOWN -> Color(0x33E74C3C)
        PriceChangeDirection.NONE -> Color.Transparent
    }

    val animatedBg by animateColorAsState(targetValue = targetColor, label = "flashColor")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(animatedBg)
            .clickable { onClick(stock.symbol) }
            .padding(16.dp)
            .testTag("stock_${stock.symbol}"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stock.symbol)
        Text(String.format("%.2f", stock.price))
        Text(if (stock.isUp) "↑" else "↓")
    }
}