package com.example.feature.deck.presentation.ui.screen

import androidx.compose.animation.animateContentSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.designsystem.R
import com.example.common.designsystem.dimens.Dimens.default
import com.example.common.designsystem.dimens.Dimens.defaultAlt
import com.example.common.designsystem.dimens.Dimens.medium
import com.example.common.designsystem.dimens.Dimens.small
import com.example.common.designsystem.dimens.Dimens.tiny
import com.example.common.designsystem.dimens.responsiveDp
import com.example.common.designsystem.theme.DeckTheme
import com.example.common.designsystem.theme.backgroundGradientColor
import com.example.common.domain.model.Card
import com.example.common.domain.model.Deck
import com.example.common.presentation.components.ImageCard
import com.example.feature.deck.presentation.ui.component.ActionButton
import com.example.feature.deck.presentation.ui.screen.DeckScreenViewModel.Companion.HAND_PILE
import com.example.feature.deck.presentation.ui.screen.DeckScreenViewModel.Companion.TRASH_PILE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
internal fun DeckScreen(
    state: DeckScreenContract.State,
    effect: Flow<DeckScreenContract.Effect>,
    onEvent: (DeckScreenContract.Event) -> Unit
) {
    var isCardFromDeck by remember {
        mutableStateOf(false)
    }
    var isCardFromTrash by remember {
        mutableStateOf(false)
    }
    var isCardToDeck by remember {
        mutableStateOf(false)
    }
    var isCardToTrash by remember {
        mutableStateOf(false)
    }

    fun wayToDraw(
        fromDeck: Boolean = false,
        fromTrash: Boolean = false,
        toDeck: Boolean = false,
        toTrash: Boolean = false
    ) {
        isCardFromDeck = fromDeck
        isCardFromTrash = fromTrash
        isCardToDeck = toDeck
        isCardToTrash = toTrash
    }

    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                DeckScreenContract.Effect.DrawCardToHand -> {
                    wayToDraw(fromDeck = true)
                }

                DeckScreenContract.Effect.ReturnCardToDeck -> {
                    wayToDraw(toDeck = true)
                }

                DeckScreenContract.Effect.MoveCardToTrash -> {
                    wayToDraw(toTrash = true)
                }

                DeckScreenContract.Effect.ReturnCardToHand -> {
                    wayToDraw(fromTrash = true)
                }

                DeckScreenContract.Effect.ShuffleDeck -> {
                    wayToDraw()
                }

                DeckScreenContract.Effect.ShufflePile -> {
                    wayToDraw()
                }
            }
        }
    }

    DeckScreenContent(
        deck = state.deck,
        isLoading = state.isScreenLoading,
        onEvent = onEvent,
        isCardFromDeck = isCardFromDeck,
        isCardFromTrash = isCardFromTrash,
        isCardToDeck = isCardToDeck,
        isCardToTrash = isCardToTrash,
    )
}

@Composable
internal fun DeckScreenContent(
    deck: Deck,
    isLoading: Boolean,
    onEvent: (DeckScreenContract.Event) -> Unit,
    isCardFromDeck: Boolean,
    isCardFromTrash: Boolean,
    isCardToDeck: Boolean,
    isCardToTrash: Boolean,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val handCards = deck.piles[HAND_PILE]?.cards ?: listOf()
    val backCard = R.drawable.back_card_example

    var isAnimating by remember { mutableStateOf(false) }
    var animatedCard by remember { mutableStateOf<Card?>(null) }

    val cardRotation by animateFloatAsState(
        targetValue = if (isAnimating) 180f else 0f,
        animationSpec = tween(durationMillis = 800)
    )
    val cardOffsetY by animateFloatAsState(
        targetValue = if (isAnimating) 500f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    LaunchedEffect(deck) {
        if (!isAnimating && deck.remainingCards > 0 && isCardFromDeck) {
            animatedCard = handCards.last()
            isAnimating = true

            scope.launch {
                delay(1500)
                isAnimating = false
            }
        }
    }

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
                    image = backCard, contentDescription = "Back of card"
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
                    onClick = { onEvent(DeckScreenContract.Event.ShuffleDeck) },
                )
                Spacer(modifier = Modifier.height(small))
                ActionButton(
                    iconId = R.drawable.get_card,
                    onClick = {
                        onEvent(DeckScreenContract.Event.DrawCardToHand)
                    },
                )
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
                        onClick = { onEvent(DeckScreenContract.Event.ShufflePile(TRASH_PILE)) },
                    )
                    Spacer(modifier = Modifier.height(small))
                    ActionButton(
                        iconId = R.drawable.resource_return,
                        onClick = { onEvent(DeckScreenContract.Event.ReturnCardToHand) },
                    )
                }
                Spacer(modifier = Modifier.width(small))
                ImageCard(image = backCard, contentDescription = "Back of card")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.End
            ) {
                Row(modifier = Modifier.padding(end = defaultAlt)) {
                    ActionButton(
                        iconId = R.drawable.shuffle,
                        onClick = { onEvent(DeckScreenContract.Event.ShufflePile(HAND_PILE)) },
                    )
                    Spacer(modifier = Modifier.width(small))
                    ActionButton(
                        iconId = R.drawable.resource_return,
                        onClick = {
                            if (handCards.isNotEmpty()) {
                                onEvent(DeckScreenContract.Event.ReturnCardToDeck)
                            }
                        },
                    )
                    Spacer(modifier = Modifier.width(small))
                    ActionButton(
                        iconId = R.drawable.trash,
                        onClick = { onEvent(DeckScreenContract.Event.MoveCardToTrash) },
                    )
                }
                Spacer(modifier = Modifier.height(small))
                LazyRow(
                    modifier = Modifier
                        .height(150.dp.responsiveDp())
                        .fillMaxWidth()
                        .background(DeckTheme.colors.surfaceColor)
                        .padding(start = default),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(handCards.reversed()) { card ->
                        ImageCard(
                            image = card.image,
                            contentDescription = "Front of card"
                        )
                        Spacer(modifier = Modifier.width(tiny))
                    }
                }
            }
            if (isAnimating && animatedCard != null) {
                Box(
                    Modifier
                        .size(150.dp.responsiveDp())
                        .offset(y = cardOffsetY.dp)
                        .graphicsLayer {
                            rotationY = cardRotation
                            cameraDistance = 12f * density.density // Make 3D flip look natural
                        }
                        .align(Alignment.TopCenter)
                        .animateContentSize(animationSpec = tween(800)),
                    contentAlignment = Alignment.Center,
                ) {
                    if (cardRotation > 90f) {
                        ImageCard(image = animatedCard?.image, contentDescription = "Front of card")
                    } else {
                        ImageCard(image = backCard, contentDescription = "Back of card")
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
            onEvent = {},
            isCardFromDeck = false,
            isCardFromTrash = false,
            isCardToDeck = false,
            isCardToTrash = false,
        )
    }
}