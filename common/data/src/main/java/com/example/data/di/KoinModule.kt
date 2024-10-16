package com.example.data.di

import android.content.Context
import com.example.data.service.local.SharedPreferencesService
import com.example.data.service.local.SharedPreferencesServiceImpl
import com.example.data.service.remote.DeckOfCardApiService
import com.example.data.service.remote.DeckOfCardApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
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
        HttpClient(CIO.create()) {
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