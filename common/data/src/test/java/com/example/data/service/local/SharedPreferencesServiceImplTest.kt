package com.example.data.service.local

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.example.data.util.Mocks.DECK_ID
import com.example.data.util.Mocks.DECK_ID2
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesServiceImplTest {

    private lateinit var sharedPreferencesService: SharedPreferencesService
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        sharedPreferencesService = SharedPreferencesServiceImpl(sharedPreferences)
    }

    @After
    fun tearDown() {
        sharedPreferences.edit().clear()
    }


    @Test
    fun `Given a deckId to save When call getDecksId Then it should return 1 id`() = runTest {
        val deckId = DECK_ID

        val expectedResponse = listOf(deckId)

        sharedPreferencesService.saveDeckId(deckId)

        val response = sharedPreferencesService.getDeckIds()

        assertEquals(expectedResponse.first(), response.first())
    }

    @Test
    fun `Given an already savedId When call deleteDeckId Then it should return a list without this id`() =
        runTest {
            val deckId = DECK_ID
            val deckId2 = DECK_ID2

            val expectedResponse = listOf(deckId)

            sharedPreferencesService.saveDeckId(deckId)

            sharedPreferencesService.saveDeckId(deckId2)

            sharedPreferencesService.deleteDeckId(deckId2)

            val response = sharedPreferencesService.getDeckIds()

            assertEquals(expectedResponse.first(), response.first())
        }
}
