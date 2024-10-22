package com.example.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PileData(
    @SerialName("remaining")
    val remainingCards: Int,

    @SerialName("cards")
    val cards: List<CardData>? = null,
)
