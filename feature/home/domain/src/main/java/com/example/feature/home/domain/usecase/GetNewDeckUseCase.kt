package com.example.feature.home.domain.usecase

import com.example.common.domain.model.RequestState

interface GetNewDeckUseCase {

    suspend operator fun invoke(): RequestState<List<String>>
}