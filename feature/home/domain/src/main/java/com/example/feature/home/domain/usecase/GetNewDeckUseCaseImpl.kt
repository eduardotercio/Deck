package com.example.feature.home.domain.usecase

import com.example.common.domain.model.RequestState
import com.example.feature.home.domain.repository.HomeRepository

class GetNewDeckUseCaseImpl(
    private val repository: HomeRepository
) : GetNewDeckUseCase {

    override suspend fun invoke(): RequestState<List<String>> {
        return repository.getNewDeck()
    }
}