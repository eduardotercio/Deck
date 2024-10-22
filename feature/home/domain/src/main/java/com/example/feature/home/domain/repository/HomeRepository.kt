package com.example.feature.home.domain.repository

import com.example.common.domain.model.Deck
import com.example.common.domain.model.RequestState

interface HomeRepository {

    suspend fun getDeckIds(): RequestState<List<String>>

    suspend fun getNewDeck(): RequestState<Deck>

    suspend fun deleteDeck(deckId:String): RequestState<Unit>

}