package com.example.common.designsystem.dimens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Dimens {
    val tinyAlt: Dp @Composable get() = 2.responsiveDp
    val tiny: Dp @Composable get() = 4.responsiveDp
    val smallAlt: Dp @Composable get() = 6.responsiveDp
    val small: Dp @Composable get() = 8.responsiveDp
    val defaultAlt: Dp @Composable get() = 12.responsiveDp
    val default: Dp @Composable get() = 16.responsiveDp
    val mediumAlt: Dp @Composable get() = 20.responsiveDp
    val medium: Dp @Composable get() = 24.responsiveDp
    val bigAlt: Dp @Composable get() = 28.responsiveDp
    val big: Dp @Composable get() = 32.responsiveDp
    val biggerAlt: Dp @Composable get() = 40.responsiveDp
    val bigger: Dp @Composable get() = 48.responsiveDp
    val hugeAlt: Dp @Composable get() = 56.responsiveDp
    val huge: Dp @Composable get() = 64.responsiveDp
}

inline val Int.responsiveDp: Dp @Composable get() = this.dp.responsiveDp()

@Composable
fun Dp.responsiveDp(): Dp {
    val density = LocalDensity.current.density

    val xhdpi = 2F
    val hdpi = 1.5F

    val floatDp = this.value

    return when {
        density >= xhdpi -> floatDp.dp
        density >= hdpi -> (floatDp * 0.83).dp
        else -> (floatDp * 0.85).dp
    }
}