package com.example.zapery.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.os.Build

private val DarkColors: ColorScheme = darkColorScheme(
    primary = Color(0xFF0EA5A4),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF7CD4D2),
    onSecondary = Color(0xFF0A1F21),
    tertiary = Color(0xFFFFB74D),
    background = Color(0xFF0F1115),
    onBackground = Color(0xFFE6EAF2),
    surface = Color(0xFF14161A),
    onSurface = Color(0xFFE6EAF2),
    error = Color(0xFFCF6679)
)

private val LightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF0EA5A4),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF7CD4D2),
    onSecondary = Color(0xFF0B2324),
    tertiary = Color(0xFFFFB74D),
    background = Color(0xFFF7F7FB),
    onBackground = Color(0xFF111827),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF111827),
    error = Color(0xFFB00020)
)

@Composable
fun ZaperyThemeM3(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colors = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme -> dynamicDarkColorScheme(context)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !darkTheme -> dynamicLightColorScheme(context)
        darkTheme -> DarkColors
        else -> LightColors
    }
    MaterialTheme(
        colorScheme = colors,
        typography = Typography3,
        shapes = Shapes3,
        content = content
    )
}
