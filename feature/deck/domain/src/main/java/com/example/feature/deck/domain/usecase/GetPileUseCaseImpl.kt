package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.Deck
import com.example.feature.deck.domain.repository.DeckRepository

class GetPileUseCaseImpl(
    private val repository: DeckRepository
) : GetPileUseCase {

    override suspend fun invoke(deckId: String, pileName: String): Result<Deck> {
        return repository.getPile(deckId, pileName)
    }

}