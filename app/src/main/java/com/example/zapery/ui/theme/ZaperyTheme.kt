package com.example.zapery.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Palette B: Neutros + Azul Petróleo
// Primária: #006D77 (azul petróleo)
// Secundária: #83C5BE (teal suave)
// Terciária/acento (usaremos em secondaryVariant): #E1AD01 (mostarda)
// Neutros claros: background #F5F6F7, surface #E9ECEF
// Neutros escuros: background #202124, surface #303134

private val DarkColorPalette = darkColors(
    primary = Color(0xFF006D77),
    primaryVariant = Color(0xFF005962),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF83C5BE),
    secondaryVariant = Color(0xFFE1AD01),
    onSecondary = Color(0xFF102A2E),
    background = Color(0xFF202124),
    onBackground = Color(0xFFECEFF1),
    surface = Color(0xFF303134),
    onSurface = Color(0xFFECEFF1),
    error = Color(0xFFCF6679)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF006D77),
    primaryVariant = Color(0xFF005962),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF83C5BE),
    secondaryVariant = Color(0xFFE1AD01),
    onSecondary = Color(0xFF0B2023),
    background = Color(0xFFF5F6F7),
    onBackground = Color(0xFF1F2930),
    surface = Color(0xFFE9ECEF),
    onSurface = Color(0xFF1F2930),
    error = Color(0xFFB00020)
)

@Composable
fun ZaperyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
