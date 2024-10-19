package com.example.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PileResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("deck_id")
    val deckId: String,

    @SerialName("remaining")
    val remainingCards: Int? = null,

    @SerialName("cards")
    val cards: List<CardData>? = null,

    @SerialName("piles")
    val piles: Map<String, PileData>
)
