package com.example.data.service.remote

import com.example.data.model.DeckResponse
import com.example.data.model.PileResponse

interface DeckOfCardApiService {

    suspend fun getNewDeck(): DeckResponse

    suspend fun getPiles(deckId: String, pileName: String): PileResponse

    suspend fun drawCardFromDeck(deckId: String): DeckResponse

    suspend fun shuffleDeck(deckId: String): DeckResponse

    suspend fun returnCardToDeck(deckId: String, pileName: String, cardCode: String): PileResponse

    suspend fun addToPile(pileName: String, deckId: String, cardCode: String): PileResponse

    suspend fun shufflePile(pileName: String, deckId: String): PileResponse

    suspend fun drawCardFromPile(pileName: String, deckId: String): PileResponse
}