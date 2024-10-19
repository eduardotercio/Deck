package com.example.feature.home.domain.repository

import com.example.common.domain.model.Deck

interface HomeRepository {

    suspend fun getDeckIds(): Result<List<String>>

    suspend fun getNewDeck(): Result<Deck>

    suspend fun deleteDeck(deckId:String): Result<Unit>

}