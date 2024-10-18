package com.example.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeckResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("deck_id")
    val deckId: String,

    @SerialName("shuffled")
    val isShuffled: Boolean? = null,

    @SerialName("cards")
    val cards: List<CardData>? = null,

    @SerialName("remaining")
    val remainingCards: Int,
)
