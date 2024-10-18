package com.example.domain.repository

import com.example.domain.model.Deck
import com.example.domain.util.Const.HAND
import com.example.domain.util.Const.TRASH

interface DeckRepository {

    suspend fun getPiles(deckId: String, pileName: String): Result<Deck>

    suspend fun drawCardFromDeck(deckId: String, pileName: String = HAND): Result<Deck>

    suspend fun drawCardFromPile(deckId: String, pileName: String = TRASH): Result<Deck>

    suspend fun returnCardToDeck(
        deckId: String,
        pileName: String = HAND,
        cardCode: String
    ): Result<Deck>

    suspend fun moveCardToPile(
        pileName: String = TRASH,
        deckId: String,
        cardCode: String
    ): Result<Deck>

    suspend fun shuffleDeck(deckId: String): Result<Deck>

    suspend fun shufflePile(pileName: String, deckId: String): Result<Deck>

}