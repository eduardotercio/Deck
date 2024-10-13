package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Deck(
    val remainingCards: Int,

    val piles: List<Pile>
)
