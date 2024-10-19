package com.example.common.data.service.local

interface SharedPreferencesService {

    suspend fun getDeckIds(): List<String>

    suspend fun saveDeckId(deckId: String)

    suspend fun deleteDeckId(deckId: String)
}