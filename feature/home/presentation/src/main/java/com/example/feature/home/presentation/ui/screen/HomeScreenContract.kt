package com.example.feature.home.presentation.ui.screen

import com.example.common.presentation.base.UiEffect
import com.example.common.presentation.base.UiEvent
import com.example.common.presentation.base.UiState

object HomeScreenContract {
    interface Event : UiEvent {
        data object GetDeckIds : Event
        data object GetNewDeck : Event
        data object DeleteLastDeckSelected : Event

        data class NavigateToDeckScreen(
            val deckId: String
        ) : Event

        data class OpenConfirmationDialog(
            val deckId: String
        ) : Event

        data object CloseConfirmationDialog : Event
    }

    interface Effect : UiEffect {
        data class NavigateToDeckScreen(
            val deckId: String
        ) : Effect

        data object OpenConfirmationDialog : Effect
        data object CloseConfirmationDialog : Effect

    }

    data class State(
        val isLoadingScreen: Boolean = true,
        val isLoadingButton: Boolean = false,
        val lastDeckIdSelected: String = "",
        val deckIds: List<String> = listOf()
    ) : UiState
}