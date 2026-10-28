package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ClassicalLightColorScheme = lightColorScheme(
    primary = SealRed,
    onPrimary = Color.White,
    primaryContainer = SealRedBg,
    onPrimaryContainer = SealRed,
    secondary = BronzeGold,
    onSecondary = Color.White,
    secondaryContainer = BronzeGoldBg,
    onSecondaryContainer = BronzeGold,
    tertiary = DarkCyan,
    onTertiary = Color.White,
    background = XuanPaper,
    onBackground = InkBlack,
    surface = XuanPaperCard,
    onSurface = InkBlack,
    surfaceVariant = XuanPaperDark,
    onSurfaceVariant = InkGrey,
    outline = XuanPaperBorder,
    outlineVariant = InkWash
)

private val ClassicalDarkColorScheme = darkColorScheme(
    primary = SealRedLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF381512),
    onPrimaryContainer = Color(0xFFFFDAD6),
    secondary = BronzeGoldLight,
    onSecondary = Color(0xFF2A1F08),
    secondaryContainer = Color(0xFF453412),
    onSecondaryContainer = BronzeGoldLight,
    tertiary = Color(0xFF7FAFC4),
    onTertiary = Color(0xFF003544),
    background = Color(0xFF161413),
    onBackground = Color(0xFFEDE8E1),
    surface = Color(0xFF221F1D),
    onSurface = Color(0xFFEDE8E1),
    surfaceVariant = Color(0xFF302B28),
    onSurfaceVariant = Color(0xFFC7BFB5),
    outline = Color(0xFF4E463E),
    outlineVariant = Color(0xFF38322B)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ClassicalDarkColorScheme else ClassicalLightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.surface.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = !darkTheme
                insetsController.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
