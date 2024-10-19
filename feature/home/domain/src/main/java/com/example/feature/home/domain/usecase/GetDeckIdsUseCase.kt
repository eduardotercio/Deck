package com.example.feature.home.domain.usecase

interface GetDeckIdsUseCase {

    suspend operator fun invoke(): Result<List<String>>
}