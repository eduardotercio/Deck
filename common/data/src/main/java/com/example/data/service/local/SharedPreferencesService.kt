package com.example.data.service.local

interface SharedPreferencesService {

    suspend fun getDeckIds(): List<String>

    suspend fun saveDeckId(deckId: String)

    suspend fun deleteDeckId(deckId: String)
}