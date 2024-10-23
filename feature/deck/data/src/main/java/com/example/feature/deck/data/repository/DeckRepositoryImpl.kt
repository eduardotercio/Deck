package com.example.feature.deck.data.repository

import android.util.Log
import com.example.common.data.mapper.toDeck
import com.example.common.data.service.remote.DeckOfCardApiService
import com.example.common.domain.model.Deck
import com.example.common.domain.model.RequestState
import com.example.feature.deck.domain.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeckRepositoryImpl(
    private val serviceApi: DeckOfCardApiService
) : DeckRepository {
    override suspend fun getPile(deckId: String, pileName: String): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val pileResponse = serviceApi.getPiles(deckId, pileName)
                val deck = pileResponse.toDeck()

                RequestState.Success(deck)
            }.getOrElse { exception ->
                val errorMessage = exception.message.toString()
                Log.e(ERROR_TAG, "getPile -> $errorMessage")
                RequestState.Error(errorMessage)
            }
        }
    }

    override suspend fun drawCardFromDeck(deckId: String, pileName: String): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val drawnCardResponse = serviceApi.drawCardFromDeck(deckId)
                val cardCode = drawnCardResponse.cards?.first()?.code ?: EMPTY_CODE

                serviceApi.addToPile(pileName, deckId, cardCode)

                val pileResponse = serviceApi.getPiles(deckId, pileName)

                RequestState.Success(pileResponse.toDeck())
            }.getOrElse { exception ->
                val errorMessage = exception.message.toString()
                Log.e(ERROR_TAG, "drawCardFromDeck -> $errorMessage")
                RequestState.Error(errorMessage)
            }
        }
    }

    override suspend fun drawCardFromPile(deckId: String, pileName: String): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val drawnCardResponse = serviceApi.drawCardFromPile(pileName, deckId)
                val cardCode = drawnCardResponse.cards?.first()?.code ?: EMPTY_CODE

                serviceApi.addToPile(HAND_PILE, deckId, cardCode)

                val pileResponse = serviceApi.getPiles(deckId, pileName)

                RequestState.Success(pileResponse.toDeck())
            }.getOrElse { exception ->
                val errorMessage = exception.message.toString()
                Log.e(ERROR_TAG, "drawCardFromPile -> $errorMessage")
                RequestState.Error(errorMessage)
            }
        }
    }

    override suspend fun returnCardToDeck(
        deckId: String,
        pileName: String,
        cardCode: String
    ): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                serviceApi.returnCardToDeck(deckId, pileName, cardCode)
                val response = serviceApi.getPiles(deckId, pileName)

                RequestState.Success(response.toDeck())
            }.getOrElse { exception ->
                val errorMessage = exception.message.toString()
                Log.e(ERROR_TAG, "returnCardToDeck -> $errorMessage")
                RequestState.Error(errorMessage)
            }
        }
    }

    override suspend fun moveCardToPile(
        pileName: String,
        deckId: String,
        cardCode: String
    ): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                serviceApi.addToPile(pileName, deckId, cardCode)
                val response = serviceApi.getPiles(deckId, HAND_PILE)

                RequestState.Success(response.toDeck())
            }.getOrElse { exception ->
                val errorMessage = exception.message.toString()
                Log.e(ERROR_TAG, "moveCardToPile -> $errorMessage")
                RequestState.Error(errorMessage)
            }
        }
    }

    override suspend fun shuffleDeck(deckId: String): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.shuffleDeck(deckId)

                RequestState.Success(response.toDeck())
            }.getOrElse { exception ->
                val errorMessage = exception.message.toString()
                Log.e(ERROR_TAG, "shuffleDeck -> $errorMessage")
                RequestState.Error(errorMessage)
            }
        }
    }

    override suspend fun shufflePile(pileName: String, deckId: String): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.shufflePile(pileName, deckId)

                RequestState.Success(response.toDeck())
            }.getOrElse { exception ->
                val errorMessage = exception.message.toString()
                Log.e(ERROR_TAG, "shufflePile -> $errorMessage")
                RequestState.Error(errorMessage)
            }
        }
    }

    private companion object {
        const val ERROR_TAG = "DeckRepository: "
        const val EMPTY_CODE = ""
        const val HAND_PILE = "hand"
    }
}