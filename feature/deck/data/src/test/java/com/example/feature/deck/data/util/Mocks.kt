package com.example.feature.deck.data.util

import com.example.common.data.model.CardData
import com.example.common.data.model.DeckResponse
import com.example.common.data.model.ImagePathType
import com.example.common.data.model.PileData
import com.example.common.data.model.PileResponse

object Mocks {
    const val DECK_ID = "deckIdTest"
    const val PILE_NAME = "pileNameTest"
    const val PILE_NAME2 = "pileNameTest2"
    const val HAND_PILE = "hand"

    val card1 = CardData(
        code = "3D",
        image = "",
        imagePathTypes = ImagePathType("", ""),
        value = "3",
        suit = "DIAMONDS"
    )

    val card2 = CardData(
        code = "KD",
        image = "",
        imagePathTypes = ImagePathType("", ""),
        value = "KING",
        suit = "DIAMONDS"
    )

    val shuffledDeck = DeckResponse(
        isSuccess = true,
        deckId = DECK_ID,
        isShuffled = true,
        cards = null,
        remainingCards = 52
    )

    val deckWithCardDrawn = DeckResponse(
        isSuccess = true,
        deckId = DECK_ID,
        isShuffled = null,
        cards = listOf(card1),
        remainingCards = 51
    )

    val deckWithCardsDrawn = DeckResponse(
        isSuccess = true,
        deckId = DECK_ID,
        isShuffled = true,
        cards = listOf(card1, card2),
        remainingCards = 50
    )

    val emptyPile = PileData(
        remainingCards = 0,
        cards = null
    )

    val pileWithCard = PileData(
        remainingCards = 1,
        cards = listOf(card1)
    )

    val pileResponseWithEmptyPile = PileResponse(
        isSuccess = true,
        deckId = DECK_ID,
        remainingCards = 52,
        cards = null,
        piles = mapOf(Pair(PILE_NAME, emptyPile))
    )

    val pileResponseWithCardInPile = PileResponse(
        isSuccess = true,
        deckId = DECK_ID,
        remainingCards = 51,
        cards = null,
        piles = mapOf(Pair(PILE_NAME, pileWithCard))
    )

    val pileResponseDrawCard = PileResponse(
        isSuccess = true,
        deckId = DECK_ID,
        remainingCards = null,
        cards = listOf(card1),
        piles = mapOf(Pair(PILE_NAME, pileWithCard))
    )

    val pileResponseWith2Piles = PileResponse(
        isSuccess = true,
        deckId = DECK_ID,
        remainingCards = null,
        cards = listOf(card1),
        piles = mapOf(Pair(PILE_NAME, emptyPile), Pair(PILE_NAME2, pileWithCard))
    )

}