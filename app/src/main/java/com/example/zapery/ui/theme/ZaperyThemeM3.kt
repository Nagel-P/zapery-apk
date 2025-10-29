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
    primary = Color(0xFF006D77),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF83C5BE),
    onSecondary = Color(0xFF102A2E),
    tertiary = Color(0xFFE1AD01),
    background = Color(0xFF202124),
    onBackground = Color(0xFFECEFF1),
    surface = Color(0xFF303134),
    onSurface = Color(0xFFECEFF1),
    error = Color(0xFFCF6679)
)

private val LightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF006D77),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF83C5BE),
    onSecondary = Color(0xFF0B2023),
    tertiary = Color(0xFFE1AD01),
    background = Color(0xFFF5F6F7),
    onBackground = Color(0xFF1F2930),
    surface = Color(0xFFE9ECEF),
    onSurface = Color(0xFF1F2930),
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
