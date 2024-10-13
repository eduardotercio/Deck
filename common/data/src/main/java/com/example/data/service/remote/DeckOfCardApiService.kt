package com.example.data.service.remote

import com.example.data.model.CardsResponse
import com.example.data.model.DeckResponse
import com.example.data.model.PileResponse

interface DeckOfCardApiService {

    suspend fun getNewDeck(): DeckResponse

    suspend fun drawCardFromDeck(deckId: String): CardsResponse

    suspend fun shuffleDeck(deckId: String): DeckResponse

    suspend fun returnCardToDeck(cardCode: String, deckId: String): DeckResponse

    suspend fun addToPile(pileName: String, deckId: String, cardCode: String): PileResponse

    suspend fun shufflePile(pileName: String, deckId: String): PileResponse

    suspend fun drawCardFromPile(pileName: String, deckId: String): PileResponse
}