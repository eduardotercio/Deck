package com.example.feature.home.domain.usecase

interface DeleteDeckUseCase {

    suspend operator fun invoke(deckId: String): Result<Unit>
}