package com.example.feature.deck.presentation.ui.screen

import androidx.lifecycle.viewModelScope
import com.example.common.domain.model.RequestState
import com.example.common.presentation.base.BaseViewModel
import com.example.feature.deck.domain.model.CardLocation
import com.example.feature.deck.domain.usecase.GetPileUseCase
import com.example.feature.deck.domain.usecase.MoveCardUseCase
import com.example.feature.deck.domain.usecase.ShuffleCardsUseCase
import kotlinx.coroutines.launch

internal class DeckScreenViewModel(
    private val getPileUseCase: GetPileUseCase,
    private val moveCardUseCase: MoveCardUseCase,
    private val shuffleCardsUseCase: ShuffleCardsUseCase
) :
    BaseViewModel<DeckScreenContract.Event, DeckScreenContract.State, DeckScreenContract.Effect>() {
    override fun setInitialState() = DeckScreenContract.State()


    override fun handleEvents(event: DeckScreenContract.Event) {
        viewModelScope.launch {
            when (event) {
                is DeckScreenContract.Event.FetchDeck -> {
                    fetchDeck(event.deckId)
                }

                is DeckScreenContract.Event.DrawCardToHand -> {
                    drawCardToHand()
                }

                is DeckScreenContract.Event.ReturnCardToDeck -> {
                    returnCardToDeck()
                }

                is DeckScreenContract.Event.MoveCardToTrash -> {
                    moveCardToTrash()
                }

                is DeckScreenContract.Event.ReturnCardToHand -> {
                    returnCardToHand()
                }

                is DeckScreenContract.Event.ShuffleDeck -> {
                    shuffleDeck()
                }

                is DeckScreenContract.Event.ShufflePile -> {
                    shufflePile(event.pileName)
                }
            }
        }
    }

    private suspend fun fetchDeck(deckId: String) {
        val result = getPileUseCase(deckId, HAND_PILE)
        when (result) {
            is RequestState.Success -> {
                setState {
                    copy(
                        isScreenLoading = false,
                        deck = result.data
                    )
                }
            }

            is RequestState.Error -> {
                setState {
                    copy(
                        isScreenLoading = false,
                    )
                }
            }
        }
    }

    private suspend fun drawCardToHand() {
        val deckId = currentState.deck.deckId
        val result = moveCardUseCase(
            startLocation = CardLocation.DECK,
            endLocation = CardLocation.HAND,
            deckId = deckId,
            pileName = HAND_PILE
        )
        when (result) {
            is RequestState.Success -> {
                setEffect { DeckScreenContract.Effect.DrawCardToHand }
                setState {
                    copy(
                        deck = result.data
                    )
                }
            }

            is RequestState.Error -> {

            }
        }
    }

    private suspend fun returnCardToDeck() {
        val deckId = currentState.deck.deckId
        val cardCode = currentState.deck.piles[HAND_PILE]?.cards?.last()?.code ?: EMPTY
        val result = moveCardUseCase(
            startLocation = CardLocation.HAND,
            endLocation = CardLocation.DECK,
            deckId = deckId,
            pileName = HAND_PILE,
            cardCode = cardCode
        )
        when (result) {
            is RequestState.Success -> {
                setEffect { DeckScreenContract.Effect.ReturnCardToDeck }
                setState {
                    copy(
                        deck = result.data
                    )
                }
            }

            is RequestState.Error -> {
            }
        }
    }

    private suspend fun moveCardToTrash() {
        val deckId = currentState.deck.deckId
        val cardCode = currentState.deck.piles[HAND_PILE]?.cards?.first()?.code ?: EMPTY
        val result = moveCardUseCase(
            startLocation = CardLocation.HAND,
            endLocation = CardLocation.TRASH,
            deckId = deckId,
            pileName = TRASH_PILE,
            cardCode = cardCode
        )
        when (result) {
            is RequestState.Success -> {
                setEffect { DeckScreenContract.Effect.MoveCardToTrash }
                setState {
                    copy(
                        deck = result.data
                    )
                }
            }

            is RequestState.Error -> {
                setState {
                    copy(
                    )
                }
            }
        }
    }

    private suspend fun returnCardToHand() {
        val deckId = currentState.deck.deckId
        val result = moveCardUseCase(
            startLocation = CardLocation.TRASH,
            endLocation = CardLocation.HAND,
            deckId = deckId,
            pileName = TRASH_PILE
        )
        when (result) {
            is RequestState.Success -> {
                setEffect { DeckScreenContract.Effect.ReturnCardToHand }
                setState {
                    copy(
                        deck = result.data
                    )
                }
            }

            is RequestState.Error -> {

            }
        }
    }

    private suspend fun shuffleDeck() {
        val deckId = currentState.deck.deckId

        val result = shuffleCardsUseCase(deckId = deckId)
        when (result) {
            is RequestState.Success -> {
                setEffect { DeckScreenContract.Effect.ShuffleDeck }
                setState {
                    copy(
                        deck = result.data
                    )
                }
            }

            is RequestState.Error -> {}
        }
    }

    private suspend fun shufflePile(pileName: String) {
        val deckId = currentState.deck.deckId

        val result = shuffleCardsUseCase(deckId = deckId, pileName = pileName)
        when (result) {
            is RequestState.Success -> {
                setEffect { DeckScreenContract.Effect.ShufflePile }
                setState {
                    copy(
                        deck = result.data
                    )
                }
            }

            is RequestState.Error -> {}
        }
    }

    companion object {
        const val HAND_PILE = "hand"
        const val TRASH_PILE = "trash"

        const val EMPTY = ""
    }
}