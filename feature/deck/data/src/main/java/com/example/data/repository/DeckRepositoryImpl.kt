package com.example.data.repository

import android.util.Log
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
        return withContext(Dispatchers.IO) {
            runCatching {
                val pileResponse = serviceApi.getPiles(deckId, pileName)

                Result.success(pileResponse.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun drawCardFromDeck(deckId: String, pileName: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val drawnCardResponse = serviceApi.drawCardFromDeck(deckId)
                val cardCode = drawnCardResponse.cards?.first()?.code ?: EMPTY_CODE
                val pileResponse = serviceApi.addToPile(pileName, deckId, cardCode)

                Result.success(pileResponse.toDeck())
            }.getOrElse { exception ->
//                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun drawCardFromPile(deckId: String, pileName: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val drawnCardResponse = serviceApi.drawCardFromPile(pileName, deckId)
                val cardCode = drawnCardResponse.cards?.first()?.code ?: EMPTY_CODE
                val pileResponse = serviceApi.addToPile(pileName, deckId, cardCode)

                Result.success(pileResponse.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun returnCardToDeck(
        deckId: String,
        pileName: String,
        cardCode: String
    ): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.returnCardToDeck(deckId, pileName, cardCode)

                Result.success(response.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun moveCardToPile(
        pileName: String,
        deckId: String,
        cardCode: String
    ): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.addToPile(pileName, deckId, cardCode)

                Result.success(response.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun shuffleDeck(deckId: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.shuffleDeck(deckId)

                Result.success(response.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun shufflePile(pileName: String, deckId: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.shufflePile(pileName, deckId)

                Result.success(response.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    private companion object {
        const val ERROR_TAG = "DeckRepository: "
        const val EMPTY_CODE = ""
    }
}