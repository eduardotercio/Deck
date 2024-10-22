package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.Deck
import com.example.common.domain.model.RequestState
import com.example.feature.deck.domain.model.CardLocation
import com.example.feature.deck.domain.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoveCardUseCaseImpl(
    private val repository: DeckRepository
) : MoveCardUseCase {
    override suspend fun invoke(
        startLocation: CardLocation,
        endLocation: CardLocation,
        deckId: String,
        pileName: String,
        cardCode: String
    ): RequestState<Deck> {
        return withContext(Dispatchers.Default) {
            if (startLocation == CardLocation.DECK) {
                repository.drawCardFromDeck(
                    deckId = deckId,
                    pileName = pileName
                )
            } else if (startLocation == CardLocation.HAND && endLocation == CardLocation.DECK) {
                repository.returnCardToDeck(
                    pileName = pileName,
                    deckId = deckId,
                    cardCode = cardCode
                )
            } else if (startLocation == CardLocation.HAND && endLocation == CardLocation.TRASH) {
                repository.moveCardToPile(
                    pileName = pileName,
                    deckId = deckId,
                    cardCode = cardCode
                )
            } else if (startLocation == CardLocation.TRASH && endLocation == CardLocation.HAND) {
                repository.drawCardFromPile(
                    pileName = pileName,
                    deckId = deckId
                )
            } else {
                RequestState.Error("Invalid parameters")
            }
        }
    }
}