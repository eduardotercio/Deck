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
        remainingCards = this.remainingCards,
        piles = listOf(Pile(
            remainingCards = this.cards?.size ?: 0,
            cards = this.cards?.map { it.toCard() } ?: listOf()
        ))
    )
}

fun PileResponse.toDeck(): Deck {
    val remainingCards =
        this.remainingCards ?: (com.example.common.data.mapper.MAX_CARDS - this.piles.map { it.value.remainingCards }.sum())
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