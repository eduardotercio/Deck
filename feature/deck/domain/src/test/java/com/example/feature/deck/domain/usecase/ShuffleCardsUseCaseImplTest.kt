package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.RequestState
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

class ShuffleCardsUseCaseImplTest {

    private val repository = mockk<DeckRepository>()
    private val useCase = ShuffleCardsUseCaseImpl(repository)

    @Test
    fun `Given a deckId When call shuffleDeck Then it should return Result success`() = runTest {
        val deckId = DECK_ID

        val expectedResult = RequestState.Success(defaultDeck)
        coEvery { repository.shuffleDeck(deckId) } returns expectedResult

        val response = useCase.invoke(deckId)

        assertEquals(expectedResult, response)

        coVerify { repository.shuffleDeck(deckId) }
    }

    @Test
    fun `Given a deckId When call shufflePile Then it should return Result success`() = runTest {
        val deckId = DECK_ID
        val pileName = PILE_NAME

        val expectedResult = RequestState.Success(defaultDeck)
        coEvery { repository.shufflePile(pileName, deckId) } returns expectedResult

        val response = useCase.invoke(deckId, pileName)

        assertEquals(expectedResult, response)

        coVerify { repository.shufflePile(pileName, deckId) }
    }
}