package com.example.feature.deck.domain.usecase

import com.example.feature.deck.domain.repository.DeckRepository
import com.example.feature.deck.domain.util.Const.DECK_ID
import com.example.feature.deck.domain.util.Const.PILE_NAME
import com.example.feature.deck.domain.util.Const.defaultDeck
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetPileUseCaseImplTest {

    private val repository = mockk<DeckRepository>()
    private val useCase = GetPileUseCaseImpl(repository)

    @Test
    fun `When call getPile Then should return a Success Deck`() = runTest {
        val deckId = DECK_ID
        val pileName = PILE_NAME

        val expectedResult = Result.success(defaultDeck)
        coEvery { repository.getPile(deckId, pileName) } returns expectedResult

        val response = useCase.invoke(deckId, pileName)

        assertEquals(expectedResult, response)

        coVerify { repository.getPile(deckId, pileName) }
    }
}