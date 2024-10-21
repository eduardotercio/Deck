package com.example.feature.deck.presentation.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.designsystem.R
import com.example.common.designsystem.dimens.Dimens.defaultAlt
import com.example.common.designsystem.dimens.Dimens.medium
import com.example.common.designsystem.dimens.Dimens.small
import com.example.common.designsystem.dimens.responsiveDp
import com.example.common.designsystem.theme.DeckTheme
import com.example.common.designsystem.theme.backgroundGradientColor
import com.example.common.domain.model.Deck
import com.example.common.presentation.components.ImageCard
import com.example.feature.deck.presentation.ui.component.ActionButton
import com.example.feature.deck.presentation.ui.screen.DeckScreenViewModel.Companion.HAND_PILE
import com.example.feature.deck.presentation.ui.screen.DeckScreenViewModel.Companion.TRASH_PILE
import kotlinx.coroutines.flow.Flow

@Composable
internal fun DeckScreen(
    state: DeckScreenContract.State,
    effect: Flow<DeckScreenContract.Effect>,
    onEvent: (DeckScreenContract.Event) -> Unit
) {
//    LaunchedEffect(Unit) {
//        effect.collect { effect ->
//            when (effect) {
//
//            }
//        }
//    }

    DeckScreenContent(
        deck = state.deck,
        isLoading = state.isLoading,
        onEvent = onEvent
    )
}

@Composable
internal fun DeckScreenContent(
    deck: Deck,
    isLoading: Boolean,
    onEvent: (DeckScreenContract.Event) -> Unit
) {
    val backCard = R.drawable.back_card_example
    val frontCard = deck.piles[HAND_PILE]?.cards?.last()?.image ?: ""

    var isFlipped by remember { mutableStateOf(false) }
    var isMovedToPile by remember { mutableStateOf(false) }

    val cardRotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    val offsetY by animateFloatAsState(
        targetValue = if (isMovedToPile) 400f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradientColor)
            .padding(top = defaultAlt),
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Box(
                Modifier
                    .size(150.dp.responsiveDp())
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center,
            ) {
                ImageCard(
                    image = backCard, contentDescription = "Back of card", modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
            Spacer(modifier = Modifier.width(small))
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(start = 150.dp.responsiveDp(), top = medium),
                verticalArrangement = Arrangement.Center
            ) {
                ActionButton(
                    iconId = R.drawable.shuffle,
                    onClick = { onEvent(DeckScreenContract.Event.ShuffleDeck) }
                )
                Spacer(modifier = Modifier.height(small))
                ActionButton(
                    iconId = R.drawable.get_card,
                    onClick = {
                        onEvent(DeckScreenContract.Event.DrawCardToHand)
                        isFlipped = !isFlipped
                        isMovedToPile = !isMovedToPile
                    }
                )
            }
            Box(
                Modifier
                    .size(150.dp.responsiveDp())
                    .offset(y = offsetY.dp)
                    .graphicsLayer {
                        rotationY = cardRotationY
                        cameraDistance = 12f * density
                    }
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center,
            ) {
                if (cardRotationY <= 90f) {
                    ImageCard(image = backCard, contentDescription = "Back of card")
                } else {
                    ImageCard(image = frontCard, contentDescription = "Front of card")
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = defaultAlt),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    ActionButton(
                        iconId = R.drawable.shuffle,
                        onClick = { onEvent(DeckScreenContract.Event.ShufflePile(TRASH_PILE)) }
                    )
                    Spacer(modifier = Modifier.height(small))
                    ActionButton(
                        iconId = R.drawable.resource_return,
                        onClick = { onEvent(DeckScreenContract.Event.ReturnCardToHand) }
                    )
                }
                Spacer(modifier = Modifier.width(small))
                ImageCard(image = backCard, contentDescription = "Back of card")
            }

            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.End
            ) {
                Row(modifier = Modifier.padding(end = defaultAlt)) {
                    ActionButton(
                        iconId = R.drawable.shuffle,
                        onClick = { onEvent(DeckScreenContract.Event.ShufflePile(HAND_PILE)) }
                    )
                    Spacer(modifier = Modifier.width(small))
                    ActionButton(
                        iconId = R.drawable.resource_return,
                        onClick = { onEvent(DeckScreenContract.Event.ReturnCardToHand) }
                    )
                    Spacer(modifier = Modifier.width(small))
                    ActionButton(
                        iconId = R.drawable.trash,
                        onClick = { onEvent(DeckScreenContract.Event.MoveCardToTrash) }
                    )
                }
                Spacer(modifier = Modifier.height(small))
                LazyRow(
                    modifier = Modifier
                        .height(150.dp.responsiveDp())
                        .fillMaxWidth()
                        .background(DeckTheme.colors.surfaceColor)
                ) {
                    items(
                        items = deck.piles[HAND_PILE]?.cards ?: listOf()
                    ) {
                        ImageCard(image = it.image, contentDescription = "Front of card")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun DeckScreenContentPreview() {
    DeckTheme {
        DeckScreenContent(
            deck = Deck("", 52, mapOf()),
            isLoading = false,
            onEvent = {}
        )
    }
}