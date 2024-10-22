package com.example.common.designsystem.dimens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

inline val Int.responsiveSp: TextUnit @Composable get() = this.sp.responsiveSp()

@Composable
fun TextUnit.responsiveSp(): TextUnit {
    val density = LocalDensity.current.density

    val xhdpi = 2F

    val floatSp = this.value

    return when {
        density >= xhdpi -> floatSp.sp
        else -> (floatSp * 0.83).sp
    }
}