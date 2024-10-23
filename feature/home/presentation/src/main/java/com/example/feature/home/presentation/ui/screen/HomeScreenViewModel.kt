package com.example.feature.home.presentation.ui.screen

import androidx.lifecycle.viewModelScope
import com.example.common.domain.model.RequestState
import com.example.common.presentation.base.BaseViewModel
import com.example.feature.home.domain.usecase.DeleteDeckUseCase
import com.example.feature.home.domain.usecase.GetDeckIdsUseCase
import com.example.feature.home.domain.usecase.GetNewDeckUseCase
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getDeckIdsUseCase: GetDeckIdsUseCase,
    private val getNewDeckUseCase: GetNewDeckUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase
) :
    BaseViewModel<HomeScreenContract.Event, HomeScreenContract.State, HomeScreenContract.Effect>() {
    override fun setInitialState() = HomeScreenContract.State()

    override fun handleEvents(event: HomeScreenContract.Event) {
        viewModelScope.launch {
            when (event) {
                is HomeScreenContract.Event.GetDeckIds -> {
                    getDeckIds()
                }

                is HomeScreenContract.Event.GetNewDeck -> {
                    getNewDeck()
                }

                is HomeScreenContract.Event.DeleteDeckSelected -> {
                    deleteDeck()
                }

                is HomeScreenContract.Event.NavigateToDeckScreen -> {
                    setEffect { HomeScreenContract.Effect.NavigateToDeckScreen(event.deckId) }
                }

                is HomeScreenContract.Event.OpenConfirmationDialog -> {
                    setEffect { HomeScreenContract.Effect.OpenConfirmationDialog }
                }

                is HomeScreenContract.Event.CloseConfirmationDialog -> {
                    setEffect { HomeScreenContract.Effect.CloseConfirmationDialog }
                }
            }
        }
    }

    private suspend fun getDeckIds() {
        val result = getDeckIdsUseCase()
        when (result) {
            is RequestState.Success -> {
                setState {
                    copy(
                        isLoadingScreen = false,
                        deckIds = result.data
                    )
                }
            }

            is RequestState.Error -> {}
        }
    }

    private suspend fun getNewDeck() {
        setState { copy(isLoadingButton = true) }
        val result = getNewDeckUseCase()
        when (result) {
            is RequestState.Success -> {
                setState {
                    copy(
                        deckIds = result.data,
                        isLoadingButton = false
                    )
                }
            }

            is RequestState.Error -> {}
        }
    }

    private suspend fun deleteDeck() {
        val deckId = currentState.lastDeckIdSelected
        val result = deleteDeckUseCase(deckId)
        when (result) {
            is RequestState.Success -> {
                setEffect { HomeScreenContract.Effect.CloseConfirmationDialog }
                setState {
                    copy(
                        deckIds = result.data
                    )
                }
            }

            is RequestState.Error -> {}
        }
    }
}