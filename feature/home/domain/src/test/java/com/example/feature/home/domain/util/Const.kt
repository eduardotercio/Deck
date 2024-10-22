package com.example.feature.home.domain.util

import com.example.common.domain.model.Deck

object Const {
    const val DECK_ID = "deckIdTest"

    val defaultDeck = Deck(
        deckId = DECK_ID,
        remainingCards = 52,
        piles = mapOf()
    )
}