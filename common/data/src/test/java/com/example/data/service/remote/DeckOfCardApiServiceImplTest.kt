package com.example.data.service.remote

import com.example.data.util.Const.DECK_ID
import com.example.data.util.Const.DEFAULT_URL_PATH
import com.example.data.util.Const.PILE_ID
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class DeckOfCardApiServiceImplTest {

    @Test
    fun `when call getNewDeck then should bring a unshuffled deck`() = runTest {
        val expectedShuffle = false
        val expectedSuccess = true
        val client = getClient(
            encodedPath = "$DEFAULT_URL_PATH/new/",
            expectedJson = """{"success": true, "deck_id": "$DECK_ID", "remaining": 52, "shuffled": false}"""
        )

        val service = DeckOfCardApiServiceImpl(client)

        val response = service.getNewDeck()

        assertEquals(expectedSuccess, response.isSuccess)
        assertEquals(expectedShuffle, response.isShuffled)

        client.close()
    }

    @Test
    fun `given a deckId and pileName with KD and 3H cards, when call getPiles then should bring deck info and the pile cards`() =
        runTest {
            val expectedPileCards = 2
            val expectedSuccess = true
            val client = getClient(
                encodedPath = "$DEFAULT_URL_PATH/$DECK_ID/pile/$PILE_ID/list/",
                expectedJson = """{"success": true, "deck_id": "$DECK_ID", "remaining": 50, "piles": {"$PILE_ID": {"remaining": 2, "cards": [{"code": "KD", "image": "https://deckofcardsapi.com/static/img/KD.png", "images": {"svg": "https://deckofcardsapi.com/static/img/KD.svg", "png": "https://deckofcardsapi.com/static/img/KD.png"}, "value": "KING", "suit": "DIAMONDS"}, {"code": "3H", "image": "https://deckofcardsapi.com/static/img/3H.png", "images": {"svg": "https://deckofcardsapi.com/static/img/3H.svg", "png": "https://deckofcardsapi.com/static/img/3H.png"}, "value": "3", "suit": "HEARTS"}]}}}"""
            )

            val service = DeckOfCardApiServiceImpl(client)

            val response = service.getPiles(DECK_ID, PILE_ID)

            assertEquals(expectedSuccess, response.isSuccess)
            assertEquals(expectedPileCards, response.piles[PILE_ID]?.cards?.size)

            client.close()
        }

    @Test
    fun drawCardFromDeck() = runTest {
    }

    @Test
    fun shuffleDeck() = runTest {
    }

    @Test
    fun returnCardToDeck() = runTest {
    }

    @Test
    fun addToPile() = runTest {
    }

    @Test
    fun shufflePile() = runTest {
    }

    @Test
    fun drawCardFromPile() = runTest {
    }

    private fun getClient(encodedPath: String, expectedJson: String): HttpClient {
        val mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                encodedPath -> respond(
                    content = expectedJson,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )

                else -> error("Error: ${request.url.encodedPath}")
            }
        }

        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}