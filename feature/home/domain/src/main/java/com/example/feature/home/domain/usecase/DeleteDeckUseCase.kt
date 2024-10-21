package com.example.feature.home.domain.usecase

import com.example.common.domain.model.RequestState

interface DeleteDeckUseCase {

    suspend operator fun invoke(deckId: String): RequestState<Unit>
}