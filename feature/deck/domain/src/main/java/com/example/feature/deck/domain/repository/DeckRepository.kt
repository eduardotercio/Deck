package com.example.feature.deck.domain.repository

import com.example.common.domain.model.Deck
import com.example.common.domain.model.RequestState

interface DeckRepository {

    suspend fun getPile(deckId: String, pileName: String): RequestState<Deck>

    suspend fun drawCardFromDeck(deckId: String, pileName: String): RequestState<Deck>

    suspend fun drawCardFromPile(deckId: String, pileName: String): RequestState<Deck>

    suspend fun returnCardToDeck(
        deckId: String,
        pileName: String,
        cardCode: String
    ): RequestState<Deck>

    suspend fun moveCardToPile(
        pileName: String,
        deckId: String,
        cardCode: String
    ): RequestState<Deck>

    suspend fun shuffleDeck(deckId: String): RequestState<Deck>

    suspend fun shufflePile(pileName: String, deckId: String): RequestState<Deck>

}