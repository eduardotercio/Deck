package com.example.common.presentation.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object Home : Route()

    @Serializable
    data class Deck(
        val deckId: String
    ) : Route()
}