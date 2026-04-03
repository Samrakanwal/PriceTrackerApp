package com.example.pricetrackerapp.data.repository
import com.example.stocktracker.data.websocket.PriceWebSocketClient

import com.example.stocktracker.data.model.StockPrice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

object StockRepository {

    private val symbols = listOf(
        "AAPL", "GOOG", "TSLA", "AMZN", "MSFT",
        "NVDA", "META", "NFLX", "AMD", "INTC",
        "ORCL", "IBM", "UBER", "LYFT", "BABA",
        "SHOP", "CRM", "ADBE", "PYPL", "SNOW",
        "PLTR", "SQ", "SONY", "SAP", "QCOM"
    )

    private val ws = PriceWebSocketClient()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _stocks = MutableStateFlow(
        symbols.associateWith {
            StockPrice(it, 100.0 + Random.nextDouble(500.0), 100.0)
        }
    )
    val stocks: StateFlow<Map<String, StockPrice>> = _stocks.asStateFlow()

    private val _connected = MutableStateFlow(false)
    val connected = _connected.asStateFlow()

    private var running = false

    fun start() {
        if (running) return
        running = true

        scope.launch {
            ws.connect().collect { raw ->
                val parts = raw.split(":")
                if (parts.size == 2) {
                    val symbol = parts[0]
                    val price = parts[1].toDoubleOrNull() ?: return@collect

                    val current = _stocks.value[symbol] ?: return@collect
                    val updated = current.copy(
                        previousPrice = current.price,
                        price = price
                    )

                    _stocks.value = _stocks.value.toMutableMap().apply {
                        put(symbol, updated)
                    }
                    _connected.value = true
                }
            }
        }

        scope.launch {
            while (running) {
                symbols.forEach { symbol ->
                    val current = _stocks.value[symbol]!!
                    val next = (current.price + Random.nextDouble(-5.0, 5.0))
                        .coerceAtLeast(1.0)
                    ws.send("$symbol:$next")
                }
                delay(2000)
            }
        }
    }

    fun stop() {
        running = false
        ws.disconnect()
        _connected.value = false
    }
}