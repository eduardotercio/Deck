package com.example.common.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class DeckColors(
    val primaryColor: Color,
    val onPrimaryColor: Color,
    val backgroundColor: Color,
    val onBackgroundColor: Color,
    val surfaceColor: Color,
    val onSurfaceColor: Color,
    val textPrimary: Color,
    val textSecondary: Color,
)

private val LocalDesignSystemThemeColors = staticCompositionLocalOf {
    DeckColors(
        primaryColor = Color.Unspecified,
        onPrimaryColor = Color.Unspecified,
        backgroundColor = Color.Unspecified,
        onBackgroundColor = Color.Unspecified,
        surfaceColor = Color.Unspecified,
        onSurfaceColor = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
    )
}

val designSystemThemePalette = DeckColors(
    primaryColor = Color(0xFF181818),
    onPrimaryColor = Color(0xFFD9D9D9),
    backgroundColor = Color(0xFF000000),
    onBackgroundColor = Color(0xFFFFFFFF),
    surfaceColor = Color(0xFF1E1E1E),
    onSurfaceColor = Color(0xFFD9D9D9),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFA8A8A8)
)

@Composable
fun DeckTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalDesignSystemThemeColors provides designSystemThemePalette,
    ) {
        MaterialTheme(
            colors = MaterialTheme.colors.copy(
                background = designSystemThemePalette.backgroundColor,
                onBackground = designSystemThemePalette.onBackgroundColor,
                surface = designSystemThemePalette.surfaceColor,
                onSurface = designSystemThemePalette.onSurfaceColor,
                primary = designSystemThemePalette.primaryColor,
                onPrimary = designSystemThemePalette.onPrimaryColor
            ),
            content = content
        )
    }
}

object DeckTheme {
    val colors: DeckColors
        @Composable get() = LocalDesignSystemThemeColors.current
}