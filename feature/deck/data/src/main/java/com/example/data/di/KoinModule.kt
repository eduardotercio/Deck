package com.example.data.di

import com.example.data.repository.DeckRepositoryImpl
import com.example.domain.repository.DeckRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureDeckDataModule = module {
    singleOf(::DeckRepositoryImpl) bind DeckRepository::class
}