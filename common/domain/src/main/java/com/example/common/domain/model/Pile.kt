package com.example.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Pile(
    val remainingCards: Int,

    val cards: List<Card>
)
