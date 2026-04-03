package com.example.stocktracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.pricetrackerapp.ui.screens.FeedScreen
import com.example.pricetrackerapp.ui.screens.SymbolDetailsScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "feed") {
        composable("feed") {
            FeedScreen(
                onClick = { symbol ->
                    navController.navigate("details/$symbol")
                }
            )
        }

        composable(
            route = "details/{symbol}",
            arguments = listOf(
                navArgument("symbol") { type = NavType.StringType }
            ),
            deepLinks = listOf(
                navDeepLink { uriPattern = "stocks://symbol/{symbol}" }
            )
        ) {
            SymbolDetailsScreen()
        }
    }
}