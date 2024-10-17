package com.example.data.util

import com.example.data.model.CardData
import com.example.data.model.DeckResponse
import com.example.data.model.ImagePathType
import com.example.data.model.PileData
import com.example.data.model.PileResponse

object Mocks {
    const val DECK_ID = "deckIdTest"
    const val PILE_NAME = "pileNameTest"

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

    val defaultDeck = DeckResponse(
        isSuccess = true,
        deckId = DECK_ID,
        isShuffled = false,
        cards = listOf(),
        remainingCards = 52
    )

    val shuffledDeck = DeckResponse(
        isSuccess = true,
        deckId = DECK_ID,
        isShuffled = null,
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

}