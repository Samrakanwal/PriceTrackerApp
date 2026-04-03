package com.example.stocktracker.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricetrackerapp.data.repository.StockRepository
import com.example.stocktracker.data.model.StockPrice

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val symbol: String = checkNotNull(savedStateHandle["symbol"])

    val stock: StateFlow<StockPrice?> = StockRepository.stocks
        .map { it[symbol] }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), null)
}