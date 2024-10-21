package com.example.common.data.di

import android.content.Context
import com.example.common.data.service.local.SharedPreferencesService
import com.example.common.data.service.local.SharedPreferencesServiceImpl
import com.example.common.data.service.remote.DeckOfCardApiService
import com.example.common.data.service.remote.DeckOfCardApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val TIME_OUT = 15000L
private const val PREFERENCES_NAME = "preferences"

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
//            install(HttpTimeout) {
//                requestTimeoutMillis = TIME_OUT
//            }
        }
    }

    single<SharedPreferencesService> {
        SharedPreferencesServiceImpl(
            sharedPreferences = androidContext().getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        )
    }

    singleOf(::DeckOfCardApiServiceImpl) bind DeckOfCardApiService::class
}