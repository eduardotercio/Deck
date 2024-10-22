package com.example.feature.home.domain.usecase

import com.example.common.domain.model.RequestState
import com.example.feature.home.domain.repository.HomeRepository
import com.example.feature.home.domain.util.Const.DECK_ID
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class DeleteDeckUseCaseImplTest {

    private val repository = mockk<HomeRepository>()
    private val useCase = DeleteDeckUseCaseImpl(repository)

    @Test
    fun `When call getNewDeck Then it should return a success list string`() = runTest {
        val deckId = DECK_ID

        val expectedResponse = RequestState.Success(Unit)
        coEvery { repository.deleteDeck(deckId) } returns expectedResponse

        val response = useCase.invoke(deckId)

        assertEquals(expectedResponse, response)

        coVerify { repository.deleteDeck(deckId) }
    }
}