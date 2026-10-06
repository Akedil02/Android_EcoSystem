package com.example.ecosystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    onPrimary = DarkBackground,
    primaryContainer = MossGreen,
    onPrimaryContainer = DarkOnSurface,
    secondary = DarkGreen,
    onSecondary = DarkBackground,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = DarkOnSurface,
    tertiary = LeafGreen,
    onTertiary = PureWhite,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurface,
    outline = DarkOutline,
    error = DarkError,
    onError = DarkBackground
)

private val LightColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = PureWhite,
    primaryContainer = SoftGreen,
    onPrimaryContainer = LightOnSurface,
    secondary = MossGreen,
    onSecondary = PureWhite,
    secondaryContainer = SoftGreen,
    onSecondaryContainer = LightOnSurface,
    tertiary = LeafGreen,
    onTertiary = PureWhite,
    background = WarmBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurface,
    outline = LightOutline,
    error = LightError,
    onError = PureWhite
)

@Composable
fun EcoSystemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
