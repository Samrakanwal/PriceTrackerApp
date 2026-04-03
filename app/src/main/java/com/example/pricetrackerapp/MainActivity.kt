package com.example.pricetrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pricetrackerapp.ui.theme.PriceTrackerAppTheme

import com.example.stocktracker.ui.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PriceTrackerAppTheme {
                AppNavGraph()
            }
        }
    }
}