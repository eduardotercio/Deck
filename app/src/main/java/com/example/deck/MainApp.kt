package com.example.deck

import android.app.Application
import com.example.data.di.commonDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(
                commonDataModule
            )
        }
    }
}