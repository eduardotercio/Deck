package com.example.feature.home.domain.repository

import com.example.common.domain.model.RequestState

interface HomeRepository {

    suspend fun getDeckIds(): RequestState<List<String>>

    suspend fun getNewDeck(): RequestState<List<String>>

    suspend fun deleteDeck(deckId: String): RequestState<List<String>>

}