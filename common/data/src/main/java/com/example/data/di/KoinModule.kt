package com.example.data.di

import com.example.data.service.remote.DeckOfCardApiService
import com.example.data.service.remote.DeckOfCardApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val TIME_OUT = 15000L

val commonDataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIME_OUT
            }
        }
    }

    singleOf(::DeckOfCardApiServiceImpl) bind DeckOfCardApiService::class
}