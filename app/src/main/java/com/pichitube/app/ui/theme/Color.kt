package com.pichitube.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ─── Brand Colors ────────────────────────────────────────────────────────────
val PichiRed       = Color(0xFFFF2D55)
val PichiRedDark   = Color(0xFFCC0033)
val PichiRedDim    = Color(0xFF3D0010)

// ─── AMOLED True Black ───────────────────────────────────────────────────────
val AmoledBackground    = Color(0xFF000000)
val AmoledSurface       = Color(0xFF0D0D0D)
val AmoledSurfaceVar    = Color(0xFF1A1A1A)
val AmoledOutline       = Color(0xFF2E2E2E)
val AmoledOnBackground  = Color(0xFFF2F2F2)
val AmoledOnSurface     = Color(0xFFE0E0E0)
val AmoledOnSurfaceVar  = Color(0xFFB0B0B0)

// ─── Dark Colors (not full AMOLED) ───────────────────────────────────────────
val DarkBackground    = Color(0xFF0F0F0F)
val DarkSurface       = Color(0xFF181818)
val DarkSurfaceVar    = Color(0xFF222222)
val DarkOutline       = Color(0xFF3A3A3A)

// AMOLED Color Scheme
val AmoledColorScheme = darkColorScheme(
    primary = PichiRed,
    onPrimary = Color.White,
    primaryContainer = PichiRedDim,
    onPrimaryContainer = PichiRed,
    secondary = Color(0xFFFF6B6B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF3D0010),
    onSecondaryContainer = Color(0xFFFF6B6B),
    tertiary = Color(0xFFFF9500),
    onTertiary = Color.White,
    background = AmoledBackground,
    onBackground = AmoledOnBackground,
    surface = AmoledSurface,
    onSurface = AmoledOnSurface,
    surfaceVariant = AmoledSurfaceVar,
    onSurfaceVariant = AmoledOnSurfaceVar,
    outline = AmoledOutline,
    outlineVariant = Color(0xFF1C1C1C),
    error = Color(0xFFCF6679),
    onError = Color.White,
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF1C1B1F),
    inversePrimary = PichiRedDark,
    surfaceTint = PichiRed,
    scrim = Color(0x80000000),
)

// Dark Color Scheme
val DarkColorScheme = darkColorScheme(
    primary = PichiRed,
    onPrimary = Color.White,
    primaryContainer = PichiRedDim,
    onPrimaryContainer = PichiRed,
    background = DarkBackground,
    onBackground = AmoledOnBackground,
    surface = DarkSurface,
    onSurface = AmoledOnSurface,
    surfaceVariant = DarkSurfaceVar,
    onSurfaceVariant = AmoledOnSurfaceVar,
    outline = DarkOutline,
)

// Light Color Scheme
val LightColorScheme = lightColorScheme(
    primary = PichiRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD9),
    onPrimaryContainer = Color(0xFF41000B),
    secondary = Color(0xFFD32F2F),
    onSecondary = Color.White,
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF3F0F4),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF7A757F),
)
