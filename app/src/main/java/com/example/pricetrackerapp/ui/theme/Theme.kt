package com.example.pricetrackerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Green40,
    secondary = Blue40,
    background = White,
    surface = White,
    onPrimary = White,
    onBackground = Black,
    onSurface = Black
)

private val DarkColors = darkColorScheme(
    primary = Green80,
    secondary = Blue80,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Black,
    onBackground = White,
    onSurface = White
)

@Composable
fun PriceTrackerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}