package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pile(
    @SerialName("remaining")
    val remainingCards: Int,

    @SerialName("cards")
    val cards: List<CardData>? = null,
)
