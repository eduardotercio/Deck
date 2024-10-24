package com.example.feature.home.domain.usecase

import com.example.common.domain.model.RequestState
import com.example.feature.home.domain.repository.HomeRepository

class DeleteDeckUseCaseImpl(
    private val repository: HomeRepository
) : DeleteDeckUseCase {
    override suspend fun invoke(deckId: String): RequestState<List<String>> {
        return repository.deleteDeck(deckId)
    }
}