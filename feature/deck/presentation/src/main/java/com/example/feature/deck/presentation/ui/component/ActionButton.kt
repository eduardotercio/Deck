package com.example.feature.deck.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.common.designsystem.dimens.Dimens.mediumAlt
import com.example.common.designsystem.dimens.Dimens.small
import com.example.common.designsystem.theme.DeckTheme

@Composable
internal fun ActionButton(iconId: Int, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(color = DeckTheme.colors.onBackgroundColor, shape = RoundedCornerShape(100))
            .padding(small)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            modifier = Modifier.size(mediumAlt),
            tint = DeckTheme.colors.textPrimary,
            contentDescription = null,
        )
    }
}