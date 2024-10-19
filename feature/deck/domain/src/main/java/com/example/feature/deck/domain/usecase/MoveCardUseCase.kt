package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.Deck
import com.example.feature.deck.domain.model.CardLocation

interface MoveCardUseCase {

    suspend operator fun invoke(
        startLocation: CardLocation,
        endLocation: CardLocation,
        deckId: String,
        pileName: String,
        cardCode: String = ""
    ): Result<Deck>
}