package com.example.feature.home.data.di

import com.example.feature.home.data.repository.HomeRepositoryImpl
import com.example.feature.home.domain.repository.HomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureHomeDataModule = module {
    singleOf(::HomeRepositoryImpl) bind HomeRepository::class
}