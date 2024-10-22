package com.example.feature.deck.domain.di

import com.example.feature.deck.domain.usecase.GetPileUseCase
import com.example.feature.deck.domain.usecase.GetPileUseCaseImpl
import com.example.feature.deck.domain.usecase.MoveCardUseCase
import com.example.feature.deck.domain.usecase.MoveCardUseCaseImpl
import com.example.feature.deck.domain.usecase.ShuffleCardsUseCase
import com.example.feature.deck.domain.usecase.ShuffleCardsUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureDeckDomainModule = module {
    factoryOf(::MoveCardUseCaseImpl) bind MoveCardUseCase::class
    factoryOf(::ShuffleCardsUseCaseImpl) bind ShuffleCardsUseCase::class
    factoryOf(::GetPileUseCaseImpl) bind GetPileUseCase::class
}