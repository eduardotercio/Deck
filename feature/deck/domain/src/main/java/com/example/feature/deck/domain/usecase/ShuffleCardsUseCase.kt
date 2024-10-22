package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.Deck
import com.example.common.domain.model.RequestState

interface ShuffleCardsUseCase {

    suspend operator fun invoke(deckId: String, pileName: String = ""): RequestState<Deck>
}