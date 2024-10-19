package com.example.feature.home.domain.usecase

import com.example.feature.home.domain.repository.HomeRepository
import com.example.feature.home.domain.util.Const.defaultDeck
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetNewDeckUseCaseImplTest {
    private val repository = mockk<HomeRepository>()
    private val useCase = GetNewDeckUseCaseImpl(repository)

    @Test
    fun `When call getNewDeck Then it should return a success list string`() = runTest {

        val expectedResponse = Result.success(defaultDeck)
        coEvery { repository.getNewDeck() } returns expectedResponse

        val response = useCase.invoke()

        assertEquals(expectedResponse, response)

        coVerify { repository.getNewDeck() }
    }
}