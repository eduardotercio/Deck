package com.example.feature.home.domain.usecase

import com.example.feature.home.domain.repository.HomeRepository

class GetDeckIdsUseCaseImpl(
    private val repository: HomeRepository
) : GetDeckIdsUseCase {

    override suspend fun invoke(): Result<List<String>> {
        return repository.getDeckIds()
    }
}