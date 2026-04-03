package com.example.pricetrackerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocktracker.ui.details.DetailsViewModel


@Composable
fun SymbolDetailsScreen(
    vm: DetailsViewModel = viewModel()
) {
    val stock by vm.stock.collectAsState()

    stock?.let {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = it.symbol,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Price: ${String.format("%.2f", it.price)} ${if (it.isUp) "↑" else "↓"}"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${it.symbol} is tracked in real time using shared WebSocket updates."
            )
        }
    }
}