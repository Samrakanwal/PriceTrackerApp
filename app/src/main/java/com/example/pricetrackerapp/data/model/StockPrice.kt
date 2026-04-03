package com.example.stocktracker.data.model

enum class PriceChangeDirection {
    UP, DOWN, NONE
}

data class StockPrice(
    val symbol: String,
    val price: Double,
    val previousPrice: Double,
) {
    val isUp: Boolean = price >= previousPrice
    val delta: Double = price - previousPrice

    val changeDirection: PriceChangeDirection =
        when {
            price > previousPrice -> PriceChangeDirection.UP
            price < previousPrice -> PriceChangeDirection.DOWN
            else -> PriceChangeDirection.NONE
        }
}