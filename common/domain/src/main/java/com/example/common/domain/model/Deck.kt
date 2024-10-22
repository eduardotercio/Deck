package com.example.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Deck(
    val deckId: String,

    val remainingCards: Int,

    val piles: Map<String, Pile>
)
