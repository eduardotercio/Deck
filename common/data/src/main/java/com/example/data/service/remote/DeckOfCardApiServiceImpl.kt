package com.example.data.service.remote

import com.example.data.model.CardsResponse
import com.example.data.model.DeckResponse
import com.example.data.model.PileResponse
import com.example.data.util.Const.ADD
import com.example.data.util.Const.BASE_URL
import com.example.data.util.Const.CARDS
import com.example.data.util.Const.COUNT
import com.example.data.util.Const.DRAW
import com.example.data.util.Const.HAND
import com.example.data.util.Const.LIST
import com.example.data.util.Const.NEW
import com.example.data.util.Const.PILE
import com.example.data.util.Const.REMAINING
import com.example.data.util.Const.RETURN
import com.example.data.util.Const.SHUFFLE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class DeckOfCardApiServiceImpl(
    private val httpClient: HttpClient
) : DeckOfCardApiService {
    override suspend fun getNewDeck(): DeckResponse {
        val url = BASE_URL.plus(NEW)
        val request = httpClient.get(url)

        return request.body<DeckResponse>()
    }

    override suspend fun getPiles(deckId: String, pileName: String): PileResponse {
        val url = BASE_URL.plus(deckId).plus(PILE).plus(pileName).plus(LIST)
        val request = httpClient.get(url)

        return request.body<PileResponse>()
    }

    override suspend fun drawCardFromDeck(deckId: String): CardsResponse {
        val url = BASE_URL.plus(deckId).plus(DRAW)
        val request = httpClient.get(url) {
            parameter(COUNT, ONE)
        }
        return request.body<CardsResponse>()
    }

    override suspend fun shuffleDeck(deckId: String): DeckResponse {
        val url = BASE_URL.plus(deckId).plus(SHUFFLE)
        val request = httpClient.get(url) {
            parameter(REMAINING, true)
        }
        return request.body<DeckResponse>()
    }

    override suspend fun returnCardToDeck(cardCode: String, deckId: String): DeckResponse {
        val url = BASE_URL.plus(deckId).plus(PILE).plus(HAND).plus(RETURN)
        val request = httpClient.get(url) {
            parameter(CARDS, cardCode)
        }
        return request.body<DeckResponse>()
    }

    override suspend fun addToPile(
        pileName: String,
        deckId: String,
        cardCode: String
    ): PileResponse {
        val url = BASE_URL.plus(deckId).plus(PILE).plus(pileName).plus(ADD)
        val request = httpClient.get(url) {
            parameter(CARDS, cardCode)
        }
        return request.body<PileResponse>()
    }

    override suspend fun shufflePile(pileName: String, deckId: String): PileResponse {
        val url = BASE_URL.plus(deckId).plus(PILE).plus(pileName).plus(SHUFFLE)
        val request = httpClient.get(url)
        return request.body<PileResponse>()
    }

    override suspend fun drawCardFromPile(pileName: String, deckId: String): PileResponse {
        val url = BASE_URL.plus(deckId).plus(PILE).plus(pileName).plus(DRAW)
        val request = httpClient.get(url) {
            parameter(COUNT, ONE)
        }
        return request.body<PileResponse>()
    }

    private companion object {
        const val ONE = 1
    }
}