package com.example.feature.deck.domain.util

import com.example.common.domain.model.Card
import com.example.common.domain.model.Deck
import com.example.common.domain.model.Pile

object Const {
    const val DECK_ID = "deckIdTest"
    const val PILE_NAME = "pileNameTest"
    const val CARD_CODE = "3D"
    const val CARD_VALUE = "3"
    const val CARD_SUIT = "D"

    private val defaultCard = Card(
        code = CARD_CODE,
        image = "",
        value = CARD_VALUE,
        suit = CARD_SUIT
    )

    private val defaultPile = Pile(
        remainingCards = 1,
        cards = listOf(defaultCard)
    )

    val defaultDeck = Deck(
        remainingCards = 52,
        piles = listOf(defaultPile)
    )
}