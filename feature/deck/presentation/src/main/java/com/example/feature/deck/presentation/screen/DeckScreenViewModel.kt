package com.example.feature.deck.presentation.screen

import androidx.lifecycle.viewModelScope
import com.example.common.presentation.base.BaseViewModel
import com.example.feature.deck.domain.model.CardLocation
import com.example.feature.deck.domain.usecase.GetPileUseCase
import com.example.feature.deck.domain.usecase.MoveCardUseCase
import com.example.feature.deck.domain.usecase.ShuffleCardsUseCase
import kotlinx.coroutines.launch

class DeckScreenViewModel(
    private val getPileUseCase: GetPileUseCase,
    private val moveCardUseCase: MoveCardUseCase,
    private val shuffleCardsUseCase: ShuffleCardsUseCase
) :
    BaseViewModel<DeckScreenContract.Event, DeckScreenContract.State, DeckScreenContract.Effect>() {
    override fun setInitialState() = DeckScreenContract.State()

    init {
        viewModelScope.launch {
            fetchPile()
        }
    }

    override fun handleEvents(event: DeckScreenContract.Event) {
        viewModelScope.launch {
            when (event) {
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

    private suspend fun fetchPile() {
        val deckId = currentState.deck.deckId
        val result = getPileUseCase(deckId, HAND_PILE)
        if (result.isSuccess) {
            setState {
                copy(
                    loading = false,
                    deck = result.getOrNull() ?: currentState.deck
                )
            }
        } else {
            setState {
                copy(
                    loading = false,
                )
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
        if (result.isSuccess) {
            setState {
                copy(
                    deck = result.getOrNull() ?: currentState.deck
                )
            }
        } else {
            setState {
                copy(
                )
            }
        }
    }

    private suspend fun returnCardToDeck() {
        val deckId = currentState.deck.deckId
        val cardCode = currentState.deck.piles[HAND_PILE]?.cards?.first()?.code ?: EMPTY
        val result = moveCardUseCase(
            startLocation = CardLocation.HAND,
            endLocation = CardLocation.DECK,
            deckId = deckId,
            pileName = HAND_PILE,
            cardCode = cardCode
        )
        if (result.isSuccess) {
            setState {
                copy(
                    deck = result.getOrNull() ?: currentState.deck
                )
            }
        } else {
            setState {
                copy(
                )
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
        if (result.isSuccess) {
            setState {
                copy(
                    deck = result.getOrNull() ?: currentState.deck
                )
            }
        } else {
            setState {
                copy(
                )
            }
        }
    }

    private suspend fun returnCardToHand() {
        val deckId = currentState.deck.deckId
        val result = moveCardUseCase(
            startLocation = CardLocation.TRASH,
            endLocation = CardLocation.HAND,
            deckId = deckId,
            pileName = HAND_PILE
        )
        if (result.isSuccess) {
            setState {
                copy(
                    deck = result.getOrNull() ?: currentState.deck
                )
            }
        } else {
            setState {
                copy(
                )
            }
        }
    }

    private suspend fun shuffleDeck() {
        val deckId = currentState.deck.deckId

        val result = shuffleCardsUseCase(deckId = deckId)
        if (result.isSuccess) {
            setState {
                copy(
                    deck = result.getOrNull() ?: currentState.deck
                )
            }
        } else {
            setState {
                copy(
                )
            }
        }
    }

    private suspend fun shufflePile(pileName: String) {
        val deckId = currentState.deck.deckId

        val result = shuffleCardsUseCase(deckId = deckId, pileName = pileName)
        if (result.isSuccess) {
            setState {
                copy(
                    deck = result.getOrNull() ?: currentState.deck
                )
            }
        } else {
            setState {
                copy(
                )
            }
        }
    }

    private companion object {
        const val HAND_PILE = "hand"
        const val TRASH_PILE = "trash"

        const val EMPTY = ""
    }
}