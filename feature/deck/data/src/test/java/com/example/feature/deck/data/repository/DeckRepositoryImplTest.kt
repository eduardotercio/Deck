package com.example.feature.deck.data.repository

import com.example.common.data.mapper.toDeck
import com.example.common.data.service.remote.DeckOfCardApiService
import com.example.common.domain.model.RequestState
import com.example.feature.deck.data.util.Mocks.DECK_ID
import com.example.feature.deck.data.util.Mocks.HAND_PILE
import com.example.feature.deck.data.util.Mocks.PILE_NAME
import com.example.feature.deck.data.util.Mocks.card1
import com.example.feature.deck.data.util.Mocks.deckWithCardDrawn
import com.example.feature.deck.data.util.Mocks.pileResponseDrawCard
import com.example.feature.deck.data.util.Mocks.pileResponseWith2Piles
import com.example.feature.deck.data.util.Mocks.pileResponseWithCardInPile
import com.example.feature.deck.data.util.Mocks.pileResponseWithEmptyPile
import com.example.feature.deck.data.util.Mocks.shuffledDeck
import com.example.feature.deck.domain.repository.DeckRepository
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
    fun `Given a deckId and pileName, When call getPiles Then should return a pileResponse with success and the expected response`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME

            val expectedResponse = pileResponseWithEmptyPile
            coEvery { serviceApi.getPiles(deckId, pileName) } returns expectedResponse

            val actualResponse = repository.getPile(deckId, pileName)

            assertTrue(actualResponse is RequestState.Success)
            assertEquals(expectedResponse.toDeck(), actualResponse.data)

            coVerify { serviceApi.getPiles(deckId, pileName) }
        }

    @Test
    fun `Given a deckId, pileName and cardCode, when call repository drawCardFromDeck, Then it should return success and the expected response`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME
            val cardCode = card1.code

            val drawCardExpectedResponse = deckWithCardDrawn
            val expectedResponse = pileResponseWithCardInPile
            coEvery { serviceApi.drawCardFromDeck(deckId) } returns drawCardExpectedResponse
            coEvery {
                serviceApi.addToPile(
                    pileName,
                    deckId,
                    cardCode
                )
            } returns Unit
            coEvery { serviceApi.getPiles(deckId, pileName) } returns expectedResponse

            val actualResponse = repository.drawCardFromDeck(deckId, pileName)

            assertTrue(actualResponse is RequestState.Success)
            assertEquals(expectedResponse.toDeck(), actualResponse.data)

            coVerify { serviceApi.drawCardFromDeck(deckId) }
            coVerify { serviceApi.addToPile(pileName, deckId, cardCode) }
            coVerify { serviceApi.getPiles(deckId, pileName) }
        }

    @Test
    fun `Given a deckId, pileName and cardCode, when call repository drawCardFromPile, Then it should return success and the expected response`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME
            val cardCode = card1.code

            val drawCardExpectedResponse = pileResponseDrawCard
            val expectedResponse = pileResponseWithCardInPile
            coEvery {
                serviceApi.drawCardFromPile(
                    pileName,
                    deckId
                )
            } returns drawCardExpectedResponse
            coEvery {
                serviceApi.addToPile(
                    HAND_PILE,
                    deckId,
                    cardCode
                )
            } returns Unit
            coEvery { serviceApi.getPiles(deckId, pileName) } returns expectedResponse

            val actualResponse = repository.drawCardFromPile(deckId, pileName)

            assertTrue(actualResponse is RequestState.Success)
            assertEquals(expectedResponse.toDeck(), actualResponse.data)

            coVerify { serviceApi.drawCardFromPile(pileName, deckId) }
            coVerify { serviceApi.addToPile(HAND_PILE, deckId, cardCode) }
            coVerify { serviceApi.getPiles(deckId, pileName) }
        }

    @Test
    fun `Given a deckId, pileName and cardCode, when call repository returnCardToDeck, Then it should return success and the expected response`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME
            val cardCode = card1.code

            val expectedResponse = pileResponseWithEmptyPile
            coEvery {
                serviceApi.returnCardToDeck(
                    deckId,
                    pileName,
                    cardCode
                )
            } returns Unit
            coEvery { serviceApi.getPiles(deckId, pileName) } returns expectedResponse


            val actualResponse = repository.returnCardToDeck(deckId, pileName, cardCode)

            assertTrue(actualResponse is RequestState.Success)
            assertEquals(expectedResponse.toDeck(), actualResponse.data)

            coVerify { serviceApi.returnCardToDeck(deckId, pileName, cardCode) }
            coVerify { serviceApi.getPiles(deckId, pileName) }
        }

    @Test
    fun `Given a deckId, pileName and cardCode, when call repository moveCardToPile, Then it should return success and the expected response`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME
            val cardCode = card1.code

            val expectedResponse = pileResponseWith2Piles
            coEvery {
                serviceApi.addToPile(
                    pileName,
                    deckId,
                    cardCode
                )
            } returns Unit
            coEvery { serviceApi.getPiles(deckId, HAND_PILE) } returns expectedResponse

            val actualResponse = repository.moveCardToPile(pileName, deckId, cardCode)

            assertTrue(actualResponse is RequestState.Success)
            assertEquals(expectedResponse.toDeck(), actualResponse.data)

            coVerify { serviceApi.addToPile(pileName, deckId, cardCode) }
            coVerify { serviceApi.getPiles(deckId, HAND_PILE) }
        }

    @Test
    fun `Given a deckId, pileName and cardCode, when call repository shuffleDeck, Then it should return success and the expected response`() =
        runTest {
            val deckId = DECK_ID

            val expectedResponse = shuffledDeck
            coEvery { serviceApi.shuffleDeck(deckId) } returns expectedResponse

            val actualResponse = repository.shuffleDeck(deckId)

            assertTrue(actualResponse is RequestState.Success)
            assertEquals(expectedResponse.toDeck(), actualResponse.data)

            coVerify { serviceApi.shuffleDeck(deckId) }
        }

    @Test
    fun `Given a deckId, pileName and cardCode, when call repository shufflePile, Then it should return success and the expected response`() =
        runTest {
            val deckId = DECK_ID
            val pileName = PILE_NAME

            val expectedResponse = pileResponseWithEmptyPile
            coEvery { serviceApi.shufflePile(pileName, deckId) } returns expectedResponse

            val actualResponse = repository.shufflePile(pileName, deckId)

            assertTrue(actualResponse is RequestState.Success)
            assertEquals(expectedResponse.toDeck(), actualResponse.data)

            coVerify { serviceApi.shufflePile(pileName, deckId) }
        }
}