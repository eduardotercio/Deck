package com.example.feature.home.domain.util

import com.example.common.domain.model.Deck

object Const {
    const val DECK_ID = "deckIdTest"

    val defaultDeck = Deck(
        remainingCards = 52,
        piles = listOf()
    )
}