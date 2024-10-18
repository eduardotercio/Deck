package com.example.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Deck(
    val remainingCards: Int,

    val piles: List<Pile>
)
