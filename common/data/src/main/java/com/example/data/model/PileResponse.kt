package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PileResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("deck_id")
    val deckId: String,

    @SerialName("remaining")
    val cardsRemaining: Int,

    @SerialName("piles")
    val piles: Map<String, Pile>
)
