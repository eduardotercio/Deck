package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.Deck

interface ShuffleCardsUseCase {

    suspend operator fun invoke(deckId: String, pileName: String = ""): Result<Deck>
}