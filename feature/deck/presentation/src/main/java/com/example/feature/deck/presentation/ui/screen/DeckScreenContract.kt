package com.example.feature.deck.presentation.ui.screen

import com.example.common.domain.model.Deck
import com.example.common.presentation.base.UiEffect
import com.example.common.presentation.base.UiEvent
import com.example.common.presentation.base.UiState

internal object DeckScreenContract {
    interface Event : UiEvent {
        data class FetchDeck(
            val deckId: String
        ) : Event

        data object DrawCardToHand : Event
        data object ReturnCardToDeck : Event
        data object MoveCardToTrash : Event
        data object ReturnCardToHand : Event
        data object ShuffleDeck : Event
        data class ShufflePile(
            val pileName: String
        ) : Event
    }

    interface Effect : UiEffect {

    }

    data class State(
        val isLoading: Boolean = true,
        val deck: Deck = Deck(deckId = "", remainingCards = 0, piles = mapOf())
    ) : UiState
}