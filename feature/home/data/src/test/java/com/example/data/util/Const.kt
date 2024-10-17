package com.example.data.util

import com.example.data.model.DeckResponse

object Const {
    const val DECK_ID = "deckIdTest"
    const val DECK_ID2 = "deckIdTest2"

    val defaultDeck = DeckResponse(
        isSuccess = true,
        deckId = DECK_ID,
        isShuffled = false,
        cards = null,
        remainingCards = 52
    )
}