package com.example.feature.home.data.repository

import android.util.Log
import com.example.common.data.mapper.toDeck
import com.example.common.data.service.local.SharedPreferencesService
import com.example.common.data.service.remote.DeckOfCardApiService
import com.example.common.domain.model.Deck
import com.example.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val servicePreferences: SharedPreferencesService,
    private val serviceApi: DeckOfCardApiService
) : HomeRepository {
    override suspend fun getDeckIds(): Result<List<String>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = servicePreferences.getDeckIds()

                Result.success(response)
            }.getOrElse {
                Log.e("HomeRepository: ", it.message.toString())
                Result.failure(it)
            }
        }
    }

    override suspend fun getNewDeck(): Result<Deck> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = serviceApi.getNewDeck()
                servicePreferences.saveDeckId(response.deckId)

                Result.success(response.toDeck())
            }.getOrElse {
                Log.e("HomeRepository: ", it.message.toString())
                Result.failure(it)
            }
        }
    }

    override suspend fun deleteDeck(deckId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                servicePreferences.deleteDeckId(deckId)

                Result.success(Unit)
            }.getOrElse {
                Log.e("HomeRepository: ", it.message.toString())
                Result.failure(it)
            }
        }
    }
}