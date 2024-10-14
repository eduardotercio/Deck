package com.example.data.repository

import com.example.data.mapper.toDeck
import com.example.data.service.remote.DeckOfCardApiService
import com.example.domain.model.Deck
import com.example.domain.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeckRepositoryImpl(
    private val serviceApi: DeckOfCardApiService
) : DeckRepository {
    override suspend fun getPiles(deckId: String, pileName: String): Result<Deck> {
        TODO("Not yet implemented")
    }

    override suspend fun drawCardFromDeck(deckId: String, pileName: String?): Result<Deck> {
        TODO("Not yet implemented")
    }

    override suspend fun drawCardFromPile(deckId: String): Result<Deck> {
        TODO("Not yet implemented")
    }

    override suspend fun returnCardToDeck(deckId: String, cardCode: String): Result<Deck> {
        TODO("Not yet implemented")
    }

    override suspend fun moveCardToTrash(deckId: String, cardCode: String): Result<Deck> {
        TODO("Not yet implemented")
    }

    override suspend fun shuffleDeck(deckId: String): Result<Deck> {
        TODO("Not yet implemented")
    }

    override suspend fun shufflePile(pileName: String): Result<Deck> {
        TODO("Not yet implemented")
    }
}