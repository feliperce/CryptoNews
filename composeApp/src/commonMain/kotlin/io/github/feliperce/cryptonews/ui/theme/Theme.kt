package io.github.feliperce.cryptonews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    error = errorLight,
    onError = onErrorLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
)

private val DarkColorPalette = lightColors(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    error = errorDark,
    onError = onErrorDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
)

@Composable
fun CryptoNewsTheme(
    darkTheme: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colorsScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colorsScheme,
        content = content
    )
}

