package com.example.feature.home.data.repository

import com.example.common.data.service.local.SharedPreferencesService
import com.example.common.data.service.remote.DeckOfCardApiService
import com.example.common.domain.model.RequestState
import com.example.feature.home.data.util.Const.DECK_ID
import com.example.feature.home.data.util.Const.DECK_ID2
import com.example.feature.home.data.util.Const.defaultDeck
import com.example.feature.home.domain.repository.HomeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HomeRepositoryImplTest {

    private lateinit var serviceApi: DeckOfCardApiService
    private lateinit var servicePreferences: SharedPreferencesService
    private lateinit var repository: HomeRepository

    @Before
    fun setUp() {
        serviceApi = mockk<DeckOfCardApiService>()
        servicePreferences = mockk<SharedPreferencesService>()
        repository = HomeRepositoryImpl(servicePreferences, serviceApi)
    }

    @Test
    fun `Given two ids in local memory When call getDeckIds Then it should return the same ones`() =
        runTest {
            val deckId = DECK_ID
            val deckId2 = DECK_ID2

            val expectedResponse = listOf(deckId, deckId2)
            coEvery { servicePreferences.getDeckIds() } returns expectedResponse

            val response = repository.getDeckIds()

            assertTrue(response is RequestState.Success)
            response.data.forEach { assertTrue(expectedResponse.contains(it)) }

            coVerify { servicePreferences.getDeckIds() }
        }

    @Test
    fun `When call getNewDeck Then it should return a mapped deck and save deckId locally`() =
        runTest {
            val deckId2 = DECK_ID2
            val deckId = DECK_ID
            val deckResponse = defaultDeck

            val expectedResponse = listOf(deckId2, deckId)

            coEvery { serviceApi.getNewDeck() } returns deckResponse
            coEvery { servicePreferences.saveDeckId(deckResponse.deckId) } returns Unit
            coEvery { servicePreferences.getDeckIds() } returns expectedResponse

            val response = repository.getNewDeck()

            assertTrue(response is RequestState.Success)
            assertEquals(expectedResponse, response.data)

            coVerify { serviceApi.getNewDeck() }
            coVerify { servicePreferences.saveDeckId(deckResponse.deckId) }
            coVerify { servicePreferences.getDeckIds() }
        }

    @Test
    fun `Given a deckID When call removeDeck on repository Then it should call deleteDeck at servicePreferences and return success `() =
        runTest {
            val deckId = DECK_ID
            val deckId2 = DECK_ID2

            val expectedResponse = listOf(deckId, deckId2)

            coEvery { servicePreferences.deleteDeckId(deckId) } returns Unit
            coEvery { servicePreferences.getDeckIds() } returns expectedResponse

            val response = repository.deleteDeck(deckId)

            assertTrue(response is RequestState.Success)
            assertEquals(expectedResponse, response.data)

            coVerify { servicePreferences.deleteDeckId(deckId) }
            coVerify { servicePreferences.getDeckIds() }
        }
}