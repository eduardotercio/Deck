package com.example.feature.home.domain.di

import com.example.feature.home.domain.usecase.DeleteDeckUseCase
import com.example.feature.home.domain.usecase.DeleteDeckUseCaseImpl
import com.example.feature.home.domain.usecase.GetDeckIdsUseCase
import com.example.feature.home.domain.usecase.GetDeckIdsUseCaseImpl
import com.example.feature.home.domain.usecase.GetNewDeckUseCase
import com.example.feature.home.domain.usecase.GetNewDeckUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureHomeDomainModule = module {
    factoryOf(::GetDeckIdsUseCaseImpl) bind GetDeckIdsUseCase::class
    factoryOf(::GetNewDeckUseCaseImpl) bind GetNewDeckUseCase::class
    factoryOf(::DeleteDeckUseCaseImpl) bind DeleteDeckUseCase::class
}