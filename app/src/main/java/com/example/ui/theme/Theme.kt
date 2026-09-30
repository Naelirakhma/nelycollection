package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AtelierLightColorScheme = lightColorScheme(
    primary = AtelierColors.Primary,
    onPrimary = AtelierColors.OnPrimary,
    primaryContainer = AtelierColors.PrimaryContainer,
    onPrimaryContainer = AtelierColors.OnPrimaryContainer,
    inversePrimary = AtelierColors.InversePrimary,
    secondary = AtelierColors.Secondary,
    onSecondary = AtelierColors.OnSecondary,
    secondaryContainer = AtelierColors.SecondaryContainer,
    onSecondaryContainer = AtelierColors.OnSecondaryContainer,
    tertiary = AtelierColors.Tertiary,
    onTertiary = AtelierColors.OnTertiary,
    tertiaryContainer = AtelierColors.TertiaryContainer,
    onTertiaryContainer = AtelierColors.OnTertiaryContainer,
    background = AtelierColors.Surface,
    onBackground = AtelierColors.OnSurface,
    surface = AtelierColors.Surface,
    onSurface = AtelierColors.OnSurface,
    surfaceVariant = AtelierColors.SurfaceContainerHighest,
    onSurfaceVariant = AtelierColors.OnSurfaceVariant,
    surfaceTint = AtelierColors.SurfaceTint,
    inverseSurface = AtelierColors.InverseSurface,
    inverseOnSurface = AtelierColors.InverseOnSurface,
    error = AtelierColors.Error,
    onError = AtelierColors.OnError,
    errorContainer = AtelierColors.ErrorContainer,
    onErrorContainer = AtelierColors.OnErrorContainer,
    outline = AtelierColors.Outline,
    outlineVariant = AtelierColors.OutlineVariant,
    surfaceBright = AtelierColors.SurfaceBright,
    surfaceDim = AtelierColors.SurfaceDim,
    surfaceContainer = AtelierColors.SurfaceContainer,
    surfaceContainerHigh = AtelierColors.SurfaceContainerHigh,
    surfaceContainerHighest = AtelierColors.SurfaceContainerHighest,
    surfaceContainerLow = AtelierColors.SurfaceContainerLow,
    surfaceContainerLowest = AtelierColors.SurfaceContainerLowest
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = AtelierLightColorScheme,
        typography = Typography,
        content = content
    )
}
