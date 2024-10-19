package com.example.feature.home.domain.usecase

import com.example.feature.home.domain.repository.HomeRepository
import com.example.feature.home.domain.util.Const.DECK_ID
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetDeckIdsUseCaseImplTest {

    private val repository = mockk<HomeRepository>()
    private val useCase = GetDeckIdsUseCaseImpl(repository)

    @Test
    fun `When call getDeckIds Then it should return a success list string`() = runTest {

        val expectedResponse = Result.success(listOf(DECK_ID))
        coEvery { repository.getDeckIds() } returns expectedResponse

        val response = useCase.invoke()

        assertEquals(expectedResponse, response)

        coVerify { repository.getDeckIds() }
    }
}