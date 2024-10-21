package com.example.feature.home.data.repository

import android.util.Log
import com.example.common.data.mapper.toDeck
import com.example.common.data.service.local.SharedPreferencesService
import com.example.common.data.service.remote.DeckOfCardApiService
import com.example.common.domain.model.Deck
import com.example.common.domain.model.RequestState
import com.example.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val servicePreferences: SharedPreferencesService,
    private val serviceApi: DeckOfCardApiService
) : HomeRepository {
    override suspend fun getDeckIds(): RequestState<List<String>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = servicePreferences.getDeckIds()

                RequestState.Success(response)
            }.getOrElse {
                val messageError = it.message.toString()
                Log.e("HomeRepository: ", messageError)
                RequestState.Error(messageError)
            }
        }
    }

    override suspend fun getNewDeck(): RequestState<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.getNewDeck()
                servicePreferences.saveDeckId(response.deckId)

                RequestState.Success(response.toDeck())
            }.getOrElse {
                val messageError = it.message.toString()
                Log.e("HomeRepository: ", messageError)
                RequestState.Error(messageError)
            }
        }
    }

    override suspend fun deleteDeck(deckId: String): RequestState<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                servicePreferences.deleteDeckId(deckId)

                RequestState.Success(Unit)
            }.getOrElse {
                val messageError = it.message.toString()
                Log.e("HomeRepository: ", messageError)
                RequestState.Error(messageError)
            }
        }
    }
}