package com.example.domain.repository

import com.example.domain.model.Deck

interface DeckRepository {

    suspend fun getPiles(deckId: String, pileName: String): Result<Deck>

    suspend fun drawCardFromDeck(deckId: String, pileName: String? = null): Result<Deck>

    suspend fun drawCardFromPile(deckId: String): Result<Deck>

    suspend fun returnCardToDeck(deckId: String, cardCode: String): Result<Deck>

    suspend fun moveCardToTrash(deckId: String, cardCode: String): Result<Deck>

    suspend fun shuffleDeck(deckId: String): Result<Deck>

    suspend fun shufflePile(pileName: String): Result<Deck>

}