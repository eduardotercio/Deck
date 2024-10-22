package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.Deck
import com.example.common.domain.model.RequestState
import com.example.feature.deck.domain.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShuffleCardsUseCaseImpl(
    private val repository: DeckRepository
) : ShuffleCardsUseCase {

    override suspend fun invoke(deckId: String, pileName: String): RequestState<Deck> {
        return withContext(Dispatchers.Default) {
            if (pileName.isEmpty()) {
                repository.shuffleDeck(deckId)
            } else {
                repository.shufflePile(pileName, deckId)
            }
        }
    }
}