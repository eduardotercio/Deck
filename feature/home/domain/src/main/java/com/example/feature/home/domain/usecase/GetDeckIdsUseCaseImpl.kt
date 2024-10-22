package com.example.feature.home.domain.usecase

import com.example.common.domain.model.RequestState
import com.example.feature.home.domain.repository.HomeRepository

class GetDeckIdsUseCaseImpl(
    private val repository: HomeRepository
) : GetDeckIdsUseCase {

    override suspend fun invoke(): RequestState<List<String>> {
        return repository.getDeckIds()
    }
}