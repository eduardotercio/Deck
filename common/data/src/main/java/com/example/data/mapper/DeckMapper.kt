package com.example.data.mapper

import com.example.data.model.CardData
import com.example.data.model.DeckResponse
import com.example.data.model.PileData
import com.example.data.model.PileResponse
import com.example.domain.model.Card
import com.example.domain.model.Deck
import com.example.domain.model.Pile

fun DeckResponse.toDeck(): Deck {
    return Deck(
        remainingCards = this.remainingCards,
        piles = listOf(Pile(
            remainingCards = this.cards?.size ?: 0,
            cards = this.cards?.map { it.toCard() } ?: listOf()
        ))
    )
}

fun PileResponse.toDeck(): Deck {
    val remainingCards =
        this.remainingCards ?: (MAX_CARDS - this.piles.map { it.value.remainingCards }.sum())
    return Deck(
        remainingCards = remainingCards,
        piles = this.piles.map { it.value.toPile() }
    )
}

fun PileData.toPile(): Pile {
    return Pile(
        remainingCards = this.remainingCards,
        cards = this.cards?.map { it.toCard() } ?: listOf()
    )
}

fun CardData.toCard(): Card {
    val svgImage = this.imagePathTypes.svgPath
    val image = svgImage.ifEmpty { this.image }
    return Card(
        code = this.code,
        image = image,
        value = this.value,
        suit = this.suit
    )
}

private const val MAX_CARDS = 52