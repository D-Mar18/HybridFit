package com.athallah.hybridfit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = OceanBlue,
    secondary = AquaTeal,
    tertiary = AquaBright,
    background = SurfaceSoft,
    surface = SurfaceWhite,
    surfaceVariant = SurfaceMuted,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = TextSecondary.copy(alpha = 0.45f),
    outlineVariant = BorderSoft,
    surfaceTint = OceanBlue
)

private val DarkColors = darkColorScheme(
    primary = AquaBright,
    secondary = AquaTeal,
    tertiary = OceanBlue,
    background = Color(0xFF0B1833),
    surface = Color(0xFF132444),
    onPrimary = TextPrimary,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun HybridFitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
