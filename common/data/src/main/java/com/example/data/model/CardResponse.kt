package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("deck_id")
    val deckId: String,

    @SerialName("cards")
    val cards: List<CardData>,

    @SerialName("remaining")
    val cardsRemaining: Int,
)
