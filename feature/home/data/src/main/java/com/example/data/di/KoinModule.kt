package com.example.data.di

import com.example.data.repository.HomeRepositoryImpl
import com.example.domain.repository.HomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureHomeDataModule = module {
    singleOf(::HomeRepositoryImpl) bind HomeRepository::class
}