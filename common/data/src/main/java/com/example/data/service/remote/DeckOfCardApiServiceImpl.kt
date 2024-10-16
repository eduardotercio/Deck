package com.example.data.service.remote

import com.example.data.model.DeckResponse
import com.example.data.model.PileResponse
import com.example.data.util.Const.BASE_URL
import com.example.data.util.Const.CARDS
import com.example.data.util.Const.COUNT
import com.example.data.util.Const.REMAINING
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class DeckOfCardApiServiceImpl(
    private val httpClient: HttpClient
) : DeckOfCardApiService {
    override suspend fun getNewDeck(): DeckResponse {
        val url = "$BASE_URL/new/"
        val request = httpClient.get(url)

        return request.body<DeckResponse>()
    }

    override suspend fun getPiles(deckId: String, pileName: String): PileResponse {
        val url = "$BASE_URL/$deckId/pile/$pileName/list/"
        val request = httpClient.get(url)

        return request.body<PileResponse>()
    }

    override suspend fun drawCardFromDeck(deckId: String): DeckResponse {
        val url = "$BASE_URL/$deckId/draw/"
        val request = httpClient.get(url) {
            parameter(COUNT, ONE)
        }
        return request.body<DeckResponse>()
    }

    override suspend fun shuffleDeck(deckId: String): DeckResponse {
        val url = "$BASE_URL/$deckId/shuffle/"
        val request = httpClient.get(url) {
            parameter(REMAINING, true)
        }
        return request.body<DeckResponse>()
    }

    override suspend fun returnCardToDeck(deckId: String, cardCode: String): DeckResponse {
        val url = "$BASE_URL/$deckId/pile/hand/return/"
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
        val url = "$BASE_URL/$deckId/pile/$pileName/add/"
        val request = httpClient.get(url) {
            parameter(CARDS, cardCode)
        }
        return request.body<PileResponse>()
    }

    override suspend fun shufflePile(pileName: String, deckId: String): PileResponse {
        val url = "$BASE_URL/$deckId/pile/$pileName/shuffle/"
        val request = httpClient.get(url)
        return request.body<PileResponse>()
    }

    override suspend fun drawCardFromPile(pileName: String, deckId: String): PileResponse {
        val url = "$BASE_URL/$deckId/pile/$pileName/draw/"
        val request = httpClient.get(url) {
            parameter(COUNT, ONE)
        }
        return request.body<PileResponse>()
    }

    private companion object {
        const val ONE = 1
    }
}