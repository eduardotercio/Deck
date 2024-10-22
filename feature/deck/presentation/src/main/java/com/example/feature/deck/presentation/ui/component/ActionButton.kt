package com.example.feature.deck.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.common.designsystem.dimens.Dimens.bigAlt
import com.example.common.designsystem.theme.DeckTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun ActionButton(iconId: Int, onClick: () -> Unit) {
    val scope = rememberCoroutineScope()

    var enabled by rememberSaveable {
        mutableStateOf(true)
    }
    val backgroundColor = if (enabled) {
        DeckTheme.colors.onBackgroundColor
    } else {
        Color.DarkGray
    }

    IconButton(
        enabled = enabled,
        onClick = {
            enabled = false
            onClick.invoke()
            scope.launch {
                delay(1500)
                enabled = true
            }
        },
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(100))
    ) {
        if (!enabled) {
            CircularProgressIndicator()
        } else {
            Icon(
                painter = painterResource(id = iconId),
                modifier = Modifier.size(bigAlt),
                tint = DeckTheme.colors.textPrimary,
                contentDescription = null,
            )
        }
    }
}