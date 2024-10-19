package com.example.feature.home.domain.usecase

import com.example.feature.home.domain.repository.HomeRepository

class DeleteDeckUseCaseImpl(
    private val repository: HomeRepository
) : DeleteDeckUseCase {
    override suspend fun invoke(deckId: String): Result<Unit> {
        return repository.deleteDeck(deckId)
    }
}