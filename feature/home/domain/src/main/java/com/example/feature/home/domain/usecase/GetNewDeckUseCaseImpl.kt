package com.example.feature.home.domain.usecase

import com.example.common.domain.model.Deck
import com.example.feature.home.domain.repository.HomeRepository

class GetNewDeckUseCaseImpl(
    private val repository: HomeRepository
) : GetNewDeckUseCase {

    override suspend fun invoke(): Result<Deck> {
        return repository.getNewDeck()
    }
}