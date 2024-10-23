package com.example.common.data.service.local

import android.content.SharedPreferences
import android.util.Log

class SharedPreferencesServiceImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesService {
    override suspend fun getDeckIds(): List<String> {
        val stringIds = getDeckIdsString()
        Log.i("deckIds", "stringIds: $stringIds")

        return if (stringIds.isEmpty()) {
            emptyList()
        } else {
            val deckIds = stringIds.split(DIVIDER).dropLast(1)
            deckIds
        }
    }

    override suspend fun saveDeckId(deckId: String) {
        val newDeckId = "$deckId/"
        val stringIds = getDeckIdsString()
        val newValue = stringIds.plus(newDeckId)
        Log.i("Response: ", "newValue: $newValue")

        sharedPreferences.edit().putString(KEY_DECK_IDS, newValue).apply()
    }

    override suspend fun deleteDeckId(deckId: String) {
        val newDeckId = "$deckId/"
        val stringIds = getDeckIdsString()
        val newValue = stringIds.replace(newDeckId, EMPTY)

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