package com.example.common.data.service.remote

import com.example.common.data.model.DeckResponse
import com.example.common.data.model.PileResponse
import com.example.common.data.util.Const.BASE_URL
import com.example.common.data.util.Const.CARDS
import com.example.common.data.util.Const.COUNT
import com.example.common.data.util.Const.REMAINING
import com.example.common.domain.util.Const.EMPTY
import com.example.common.domain.util.Const.HAND_PILE
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

        val response = request.body<DeckResponse>()
        createDeckPiles(response.deckId)

        return response
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

    override suspend fun returnCardToDeck(
        deckId: String,
        pileName: String,
        cardCode: String
    ) {
        val url = "$BASE_URL/$deckId/pile/$pileName/return/"
        httpClient.get(url) {
            parameter(CARDS, cardCode)
        }
    }

    override suspend fun addToPile(
        pileName: String,
        deckId: String,
        cardCode: String
    ) {
        val url = "$BASE_URL/$deckId/pile/$pileName/add/"
        httpClient.get(url) {
            parameter(CARDS, cardCode)
        }
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

    private suspend fun createDeckPiles(deckId: String) {
        val createHandPileUrl = "$BASE_URL/$deckId/pile/$HAND_PILE/add/"
        httpClient.get(createHandPileUrl) {
            parameter(CARDS, EMPTY)
        }

        val createTrashPileUrl = "$BASE_URL/$deckId/pile/$HAND_PILE/add/"
        httpClient.get(createTrashPileUrl) {
            parameter(CARDS, EMPTY)
        }
    }

    private companion object {
        const val ONE = 1
    }
}