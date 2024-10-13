package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardsResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("deck_id")
    val deckId: String,

    @SerialName("cards")
    val cards: List<CardData>,

    @SerialName("remaining")
    val remainingCards: Int,
)
