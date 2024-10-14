package com.example.data.service.local

import android.content.SharedPreferences

class SharedPreferencesServiceImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesService {
    override suspend fun getDeckIds(): List<String> {
        val stringIds = getDeckIdsString()

        return if (stringIds.isEmpty()) {
            emptyList()
        } else {
            stringIds.split(DIVIDER)
        }
    }

    override suspend fun saveDeckId(deckId: String) {
        val stringIds = getDeckIdsString()
        val newValue = stringIds.plus(deckId).plus(DIVIDER)

        sharedPreferences.edit().putString(KEY_DECK_IDS, newValue).apply()
    }

    private fun getDeckIdsString(): String {
        return sharedPreferences.getString(KEY_DECK_IDS, EMPTY) ?: EMPTY
    }

    private companion object {
        const val KEY_DECK_IDS = "deck_ids"
        const val EMPTY = ""
        const val DIVIDER = "/"
    }
}