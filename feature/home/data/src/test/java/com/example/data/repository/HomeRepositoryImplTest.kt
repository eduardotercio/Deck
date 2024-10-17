package com.example.data.repository

import com.example.data.mapper.toDeck
import com.example.data.service.local.SharedPreferencesService
import com.example.data.service.remote.DeckOfCardApiService
import com.example.data.util.Const.DECK_ID
import com.example.data.util.Const.DECK_ID2
import com.example.data.util.Const.defaultDeck
import com.example.domain.repository.HomeRepository
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

            assertTrue(response.isSuccess)
            response.getOrNull()?.forEach { assertTrue(expectedResponse.contains(it)) }

            coVerify { servicePreferences.getDeckIds() }
        }

    @Test
    fun `When call getNewDeck Then it should return a mapped deck and save deckId locally`() =
        runTest {
            val expectedResponse = defaultDeck

            coEvery { serviceApi.getNewDeck() } returns expectedResponse
            coEvery { servicePreferences.saveDeckId(expectedResponse.deckId) } returns Unit

            val response = repository.getNewDeck()

            assertTrue(response.isSuccess)
            assertEquals(expectedResponse.toDeck(), response.getOrNull())

            coVerify { serviceApi.getNewDeck() }
            coVerify { servicePreferences.saveDeckId(expectedResponse.deckId) }
        }

    @Test
    fun `Given a deckID When call removeDeck on repository Then it should call deleteDeck at servicePreferences and return success `() =
        runTest {
            val deckId = DECK_ID

            coEvery { servicePreferences.deleteDeckId(deckId) } returns Unit

            val response = repository.deleteDeck(deckId)

            assertTrue(response.isSuccess)

            coVerify { servicePreferences.deleteDeckId(deckId) }
        }
}