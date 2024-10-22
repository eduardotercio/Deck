package com.example.feature.deck.domain.usecase

import com.example.common.domain.model.RequestState
import com.example.feature.deck.domain.model.CardLocation
import com.example.feature.deck.domain.repository.DeckRepository
import com.example.feature.deck.domain.util.Const.CARD_CODE
import com.example.feature.deck.domain.util.Const.DECK_ID
import com.example.feature.deck.domain.util.Const.PILE_NAME
import com.example.feature.deck.domain.util.Const.defaultDeck
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class MoveCardUseCaseImplTest {

    private val repository = mockk<DeckRepository>()
    private val useCase = MoveCardUseCaseImpl(repository)

    @Test
    fun `Given that move from deck to hand When call drawCardFromDeck Then should return Result success`() =
        runTest {
            val startLocation = CardLocation.DECK
            val endLocation = CardLocation.HAND
            val deckId = DECK_ID
            val pileName = PILE_NAME

            val expectedResponse = RequestState.Success(defaultDeck)
            coEvery { repository.drawCardFromDeck(deckId, pileName) } returns expectedResponse

            val response = useCase.invoke(startLocation, endLocation, deckId, pileName)

            assertEquals(expectedResponse, response)

            coVerify { repository.drawCardFromDeck(deckId, pileName) }
        }

    @Test
    fun `Given that move from hand to deck When call returnCardToDeck Then should return Result success`() =
        runTest {
            val startLocation = CardLocation.HAND
            val endLocation = CardLocation.DECK
            val deckId = DECK_ID
            val pileName = PILE_NAME
            val cardCode = CARD_CODE

            val expectedResponse = RequestState.Success(defaultDeck)
            coEvery {
                repository.returnCardToDeck(
                    deckId,
                    pileName,
                    cardCode
                )
            } returns expectedResponse

            val response = useCase.invoke(startLocation, endLocation, deckId, pileName, cardCode)

            assertEquals(expectedResponse, response)

            coVerify { repository.returnCardToDeck(deckId, pileName, cardCode) }
        }

    @Test
    fun `Given that move from hand to trash When call moveCardToPile Then should return Result success`() =
        runTest {
            val startLocation = CardLocation.HAND
            val endLocation = CardLocation.TRASH
            val deckId = DECK_ID
            val pileName = PILE_NAME
            val cardCode = CARD_CODE

            val expectedResponse = RequestState.Success(defaultDeck)
            coEvery {
                repository.moveCardToPile(
                    pileName,
                    deckId,
                    cardCode
                )
            } returns expectedResponse

            val response = useCase.invoke(startLocation, endLocation, deckId, pileName, cardCode)

            assertEquals(expectedResponse, response)

            coVerify {
                repository.moveCardToPile(
                    pileName,
                    deckId,
                    cardCode
                )
            }
        }

    @Test
    fun `Given that move from trash to hand When call drawCardFromPile Then should return Result success`() =
        runTest {
            val startLocation = CardLocation.TRASH
            val endLocation = CardLocation.HAND
            val deckId = DECK_ID
            val pileName = PILE_NAME

            val expectedResponse = RequestState.Success(defaultDeck)
            coEvery { repository.drawCardFromPile(deckId, pileName) } returns expectedResponse

            val response = useCase.invoke(startLocation, endLocation, deckId, pileName)

            assertEquals(expectedResponse, response)

            coVerify { repository.drawCardFromPile(deckId, pileName) }
        }
}