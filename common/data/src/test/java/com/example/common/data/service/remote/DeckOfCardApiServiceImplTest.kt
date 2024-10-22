package com.example.common.data.service.remote

import com.example.common.data.util.Mocks.DECK_ID
import com.example.common.data.util.Mocks.DEFAULT_URL_PATH
import com.example.common.data.util.Mocks.PILE_NAME
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
import kotlin.test.assertTrue

class DeckOfCardApiServiceImplTest {

    @Test
    fun `When call getNewDeck Then should bring a unshuffled deck`() = runTest {
        val expectedShuffle = false
        val expectedIsSuccess = true
        val client = getClient(
            encodedPath = "$DEFAULT_URL_PATH/new/",
            expectedJson = """{"success": true, "deck_id": "$DECK_ID", "remaining": 52, "shuffled": false}"""
        )

        val service = DeckOfCardApiServiceImpl(client)

        val response = service.getNewDeck()

        assertEquals(expectedIsSuccess, response.isSuccess)
        assertEquals(expectedShuffle, response.isShuffled)

        client.close()
    }

    @Test
    fun `Given a deckId and pileName with KD and 3H cards, When call getPiles Then should bring deck info and the pile cards`() =
        runTest {
            val expectedPileCards = 2
            val expectedIsSuccess = true
            val client = getClient(
                encodedPath = "$DEFAULT_URL_PATH/$DECK_ID/pile/$PILE_NAME/list/",
                expectedJson = """{"success": true, "deck_id": "$DECK_ID", "remaining": 50, "piles": {"$PILE_NAME": {"remaining": 2, "cards": [{"code": "KD", "image": "https://deckofcardsapi.com/static/img/KD.png", "images": {"svg": "https://deckofcardsapi.com/static/img/KD.svg", "png": "https://deckofcardsapi.com/static/img/KD.png"}, "value": "KING", "suit": "DIAMONDS"}, {"code": "3H", "image": "https://deckofcardsapi.com/static/img/3H.png", "images": {"svg": "https://deckofcardsapi.com/static/img/3H.svg", "png": "https://deckofcardsapi.com/static/img/3H.png"}, "value": "3", "suit": "HEARTS"}]}}}"""
            )

            val service = DeckOfCardApiServiceImpl(client)

            val response = service.getPiles(DECK_ID, PILE_NAME)

            assertEquals(expectedIsSuccess, response.isSuccess)
            assertEquals(expectedPileCards, response.piles[PILE_NAME]?.cards?.size)

            client.close()
        }

    @Test
    fun `Given a deckId When call drawCardFromDeck Then it should return a valid card`() = runTest {
        val expectedCardCode = "AC"
        val expectedIsSuccess = true
        val client = getClient(
            encodedPath = "$DEFAULT_URL_PATH/$DECK_ID/draw/",
            expectedJson = """{"success": true, "deck_id": "$DECK_ID", "cards": [{"code": "AC", "image": "https://deckofcardsapi.com/static/img/AC.png", "images": {"svg": "https://deckofcardsapi.com/static/img/AC.svg", "png": "https://deckofcardsapi.com/static/img/AC.png"}, "value": "ACE", "suit": "CLUBS"}], "remaining": 51}"""
        )
        val service = DeckOfCardApiServiceImpl(client)

        val response = service.drawCardFromDeck(DECK_ID)

        assertEquals(expectedIsSuccess, response.isSuccess)
        assertEquals(expectedCardCode, response.cards?.first { it.code == expectedCardCode }?.code)

        client.close()
    }

    @Test
    fun `When call shuffleDeck Then shuffle should be true`() = runTest {
        val expectedIsSuccess = true
        val expectedIsShuffled = true
        val client = getClient(
            encodedPath = "$DEFAULT_URL_PATH/$DECK_ID/shuffle/",
            expectedJson = """{"success": true, "deck_id": "u7s8sr4usyz2", "remaining": 46, "shuffled": true}"""
        )
        val service = DeckOfCardApiServiceImpl(client)

        val response = service.shuffleDeck(DECK_ID)

        assertEquals(expectedIsSuccess, response.isSuccess)
        assertEquals(expectedIsShuffled, response.isShuffled)

        client.close()
    }

    @Test
    fun `When call shufflePile Then should return the list of piles containing the pile`() =
        runTest {
            val expectedIsSuccess = true
            val client = getClient(
                encodedPath = "$DEFAULT_URL_PATH/$DECK_ID/pile/$PILE_NAME/shuffle/",
                expectedJson = """{"success": true, "deck_id": "$DECK_ID", "remaining": 46, "piles": {"$PILE_NAME": {"remaining": 2}}}"""
            )
            val service = DeckOfCardApiServiceImpl(client)

            val response = service.shufflePile(PILE_NAME, DECK_ID)

            assertEquals(expectedIsSuccess, response.isSuccess)
            assertTrue(response.piles.keys.contains(PILE_NAME))

            client.close()
        }

    @Test
    fun `Given a pileName and deckId When call drawCardFromPile Then it should return a card in the field cards`() =
        runTest {
            val expectedCardCode = "3H"
            val expectedIsSuccess = true
            val client = getClient(
                encodedPath = "$DEFAULT_URL_PATH/$DECK_ID/pile/$PILE_NAME/draw/",
                expectedJson = """{"success": true, "deck_id": "$DECK_ID", "cards": [{"code": "3H", "image": "https://deckofcardsapi.com/static/img/3H.png", "images": {"svg": "https://deckofcardsapi.com/static/img/3H.svg", "png": "https://deckofcardsapi.com/static/img/3H.png"}, "value": "3", "suit": "HEARTS"}], "piles": {"$PILE_NAME": {"remaining": 0}}}"""
            )
            val service = DeckOfCardApiServiceImpl(client)

            val response = service.drawCardFromPile(PILE_NAME, DECK_ID)

            assertEquals(expectedIsSuccess, response.isSuccess)
            assertEquals(
                expectedCardCode,
                response.cards?.first { it.code == expectedCardCode }?.code
            )

            client.close()
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