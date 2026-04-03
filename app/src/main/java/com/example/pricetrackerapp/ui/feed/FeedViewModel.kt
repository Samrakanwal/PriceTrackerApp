package com.example.stocktracker.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricetrackerapp.data.repository.StockRepository
import com.example.stocktracker.data.model.StockPrice

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class FeedUiState(
    val stocks: List<StockPrice> = emptyList(),
    val connected: Boolean = false,
    val running: Boolean = false
)

class FeedViewModel : ViewModel() {

    val uiState: StateFlow<FeedUiState> = combine(
        StockRepository.stocks,
        StockRepository.connected
    ) { stocks, connected ->
        FeedUiState(
            stocks = stocks.values.sortedByDescending { it.price },
            connected = connected,
            running = connected
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FeedUiState())

    fun start() = StockRepository.start()
    fun stop() = StockRepository.stop()
}