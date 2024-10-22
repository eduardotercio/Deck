package com.example.common.data.mapper

import com.example.common.data.model.CardData
import com.example.common.data.model.DeckResponse
import com.example.common.data.model.PileData
import com.example.common.data.model.PileResponse
import com.example.common.domain.model.Card
import com.example.common.domain.model.Deck
import com.example.common.domain.model.Pile

fun DeckResponse.toDeck(): Deck {
    return Deck(
        deckId = this.deckId,
        remainingCards = this.remainingCards,
        piles = mapOf()
    )
}

fun PileResponse.toDeck(): Deck {
    val remainingCards =
        this.remainingCards
            ?: (MAX_CARDS - this.piles.map { it.value.remainingCards }
                .sum())
    return Deck(
        deckId = this.deckId,
        remainingCards = remainingCards,
        piles = this.piles.mapValues { (_, value) ->
            value.toPile()
        }
    )
}

fun PileData.toPile(): Pile {
    return Pile(
        remainingCards = this.remainingCards,
        cards = this.cards?.map { it.toCard() } ?: listOf()
    )
}

fun CardData.toCard(): Card {
    val image = this.image
    return Card(
        code = this.code,
        image = image,
        value = this.value,
        suit = this.suit
    )
}

private const val MAX_CARDS = 52