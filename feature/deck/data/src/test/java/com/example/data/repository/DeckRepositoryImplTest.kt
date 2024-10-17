package com.example.data.repository

import com.example.data.mapper.toDeck
import com.example.data.service.remote.DeckOfCardApiService
import com.example.data.util.Mocks.DECK_ID
import com.example.data.util.Mocks.PILE_NAME
import com.example.data.util.Mocks.card1
import com.example.data.util.Mocks.deckWithCardDrawn
import com.example.data.util.Mocks.pileResponseDrawCard
import com.example.data.util.Mocks.pileResponseWithCardInPile
import com.example.data.util.Mocks.pileResponseWithEmptyPile
import com.example.domain.repository.DeckRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeckRepositoryImplTest {

    private lateinit var serviceApi: DeckOfCardApiService
    private lateinit var repository: DeckRepository

    @Before
    fun setUp() {
        serviceApi = mockk<DeckOfCardApiService>()
        repository = DeckRepositoryImpl(serviceApi)
    }

    @Test
    fun `Given a deckId and pileName, When call getPiles Then should return a pileResponse with success`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME

            val expectedResponse = pileResponseWithEmptyPile
            coEvery { serviceApi.getPiles(deckId, pileName) } returns expectedResponse

            val actualResponse = repository.getPiles(deckId, pileName)

            assertTrue(actualResponse.isSuccess)
            assertEquals(expectedResponse.toDeck(), actualResponse.getOrNull())

            coVerify { serviceApi.getPiles(deckId, pileName) }
        }

    @Test
    fun `Given a deckId, pileName and cardCode, when call repository drawCardFromDeck, Then it should return success`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME
            val cardCode = card1.code

            val drawCardExpectedResponse = deckWithCardDrawn
            val addToPileExpectedResponse = pileResponseWithCardInPile
            coEvery { serviceApi.drawCardFromDeck(deckId) } returns drawCardExpectedResponse
            coEvery {
                serviceApi.addToPile(
                    pileName,
                    deckId,
                    cardCode
                )
            } returns addToPileExpectedResponse

            val actualResponse = repository.drawCardFromDeck(deckId, pileName)

            assertTrue(actualResponse.isSuccess)
            assertEquals(addToPileExpectedResponse.toDeck(), actualResponse.getOrNull())

            coVerify { serviceApi.drawCardFromDeck(deckId) }
            coVerify { serviceApi.addToPile(pileName, deckId, cardCode) }
        }

    @Test
    fun drawCardFromTrash() = runTest {
        val deckId = DECK_ID
        val pileName = PILE_NAME
        val cardCode = card1.code

        val drawCardExpectedResponse = pileResponseDrawCard
        val addToPileExpectedResponse = pileResponseWithCardInPile
        coEvery { serviceApi.drawCardFromPile(pileName, deckId) } returns drawCardExpectedResponse
        coEvery {
            serviceApi.addToPile(
                pileName,
                deckId,
                cardCode
            )
        } returns addToPileExpectedResponse

        val actualResponse = repository.drawCardFromPile(deckId, pileName)

        assertTrue(actualResponse.isSuccess)
        assertEquals(addToPileExpectedResponse.toDeck(), actualResponse.getOrNull())

        coVerify { serviceApi.drawCardFromPile(pileName, deckId) }
        coVerify { serviceApi.addToPile(pileName, deckId, cardCode) }
    }

    @Test
    fun returnCardToDeck() = runTest {
    }

    @Test
    fun moveCardToTrash() = runTest {
    }

    @Test
    fun shuffleDeck() = runTest {
    }

    @Test
    fun shufflePile() = runTest {
    }
}