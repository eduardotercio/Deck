package com.example.domain.repository

import com.example.domain.model.Card

interface DeckRepository {

    suspend fun getPiles(deckId: String, pileName: String): List<Card>

}