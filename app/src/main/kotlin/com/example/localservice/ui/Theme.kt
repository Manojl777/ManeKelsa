package com.example.localservice.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val RawColorPrimary = Color(0xFF006766)
val RawColorSecondary = Color(0xFFF4511E)
val RawColorTertiary = Color(0xFF5D5E53)
val RawColorAppBg = Color(0xFFFFFDF5)
val RawColorSurface = Color(0xFFFFFFFF)

val ColorPrimary: Color
    @Composable get() = MaterialTheme.colorScheme.primary

val ColorSecondary: Color
    @Composable get() = MaterialTheme.colorScheme.secondary

val ColorTertiary: Color
    @Composable get() = MaterialTheme.colorScheme.tertiary

val ColorAppBg: Color
    @Composable get() = MaterialTheme.colorScheme.background

val ColorSurface: Color
    @Composable get() = MaterialTheme.colorScheme.surface

@Composable
fun ManeKelsaTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF14B8A6), // Teal-400 for dark mode
            onPrimary = Color(0xFF042F2E),
            primaryContainer = Color(0xFF0D9488),
            onPrimaryContainer = Color(0xFFCCFBF1),
            secondary = Color(0xFFF97316), // Orange-500
            onSecondary = Color(0xFF431407),
            secondaryContainer = Color(0xFFEA580C),
            onSecondaryContainer = Color(0xFFFFEDD5),
            tertiary = Color(0xFFA1A1AA), // Zinc-400
            onTertiary = Color(0xFF27272A),
            tertiaryContainer = Color(0xFF3F3F46),
            onTertiaryContainer = Color(0xFFF4F4F5),
            background = Color(0xFF09090B), // Zinc-950
            onBackground = Color(0xFFFAFAFA),
            surface = Color(0xFF18181B), // Zinc-900
            onSurface = Color(0xFFFAFAFA),
            surfaceVariant = Color(0xFF27272A),
            onSurfaceVariant = Color(0xFFA1A1AA),
            outline = Color(0xFF52525B),
            outlineVariant = Color(0xFF27272A)
        )
    } else {
        lightColorScheme(
            primary = RawColorPrimary,
            onPrimary = RawColorSurface,
            primaryContainer = RawColorPrimary.copy(alpha = 0.12f),
            onPrimaryContainer = RawColorPrimary,
            secondary = RawColorSecondary,
            onSecondary = RawColorSurface,
            secondaryContainer = RawColorSecondary.copy(alpha = 0.12f),
            onSecondaryContainer = RawColorSecondary,
            tertiary = RawColorTertiary,
            onTertiary = RawColorSurface,
            tertiaryContainer = RawColorTertiary.copy(alpha = 0.12f),
            onTertiaryContainer = RawColorTertiary,
            background = RawColorAppBg,
            onBackground = RawColorTertiary,
            surface = RawColorSurface,
            onSurface = RawColorTertiary,
            surfaceVariant = RawColorAppBg,
            onSurfaceVariant = RawColorTertiary.copy(alpha = 0.75f),
            outline = RawColorTertiary.copy(alpha = 0.28f),
            outlineVariant = RawColorTertiary.copy(alpha = 0.14f)
        )
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
