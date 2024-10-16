package com.example.data.repository

import android.util.Log
import com.example.data.mapper.toDeck
import com.example.data.service.remote.DeckOfCardApiService
import com.example.data.util.Const.HAND
import com.example.data.util.Const.TRASH
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

    override suspend fun drawCardFromDeck(deckId: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val drawnCardResponse = serviceApi.drawCardFromDeck(deckId)
                val cardCode =
                    drawnCardResponse.cards?.first().let { it?.value.plus(it?.suit) }
                val pileResponse = serviceApi.addToPile(HAND, deckId, cardCode)

                Result.success(pileResponse.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun drawCardFromTrash(deckId: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val drawnCardResponse = serviceApi.drawCardFromPile(deckId, TRASH)
                val cardCode =
                    drawnCardResponse.piles.filter { it.key == TRASH }.map { it.value }
                        .first().cards?.first().let {
                            it?.value.plus(it?.suit)
                        }
                val pileResponse = serviceApi.addToPile(TRASH, deckId, cardCode)

                Result.success(pileResponse.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun returnCardToDeck(deckId: String, cardCode: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.returnCardToDeck(deckId, HAND, cardCode)

                Result.success(response.toDeck())
            }.getOrElse { exception ->
                Log.e(ERROR_TAG, exception.message.toString())
                Result.failure(exception)
            }
        }
    }

    override suspend fun moveCardToTrash(deckId: String, cardCode: String): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.addToPile(TRASH, deckId, cardCode)

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
    }
}