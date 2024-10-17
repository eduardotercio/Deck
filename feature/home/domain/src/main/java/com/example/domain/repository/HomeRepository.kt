package com.example.domain.repository

import com.example.domain.model.Deck

interface HomeRepository {

    suspend fun getDeckIds(): Result<List<String>>

    suspend fun getNewDeck(): Result<Deck>

    suspend fun deleteDeck(deckId:String): Result<Unit>

}