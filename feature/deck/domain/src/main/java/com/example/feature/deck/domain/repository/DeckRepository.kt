package com.example.feature.deck.domain.repository

import com.example.common.domain.model.Deck

interface DeckRepository {

    suspend fun getPiles(deckId: String, pileName: String): Result<Deck>

    suspend fun drawCardFromDeck(deckId: String, pileName: String): Result<Deck>

    suspend fun drawCardFromPile(deckId: String, pileName: String): Result<Deck>

    suspend fun returnCardToDeck(
        deckId: String,
        pileName: String,
        cardCode: String
    ): Result<Deck>

    suspend fun moveCardToPile(
        pileName: String,
        deckId: String,
        cardCode: String
    ): Result<Deck>

    suspend fun shuffleDeck(deckId: String): Result<Deck>

    suspend fun shufflePile(pileName: String, deckId: String): Result<Deck>

}