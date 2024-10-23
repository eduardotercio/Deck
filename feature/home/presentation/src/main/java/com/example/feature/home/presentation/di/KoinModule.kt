package com.example.feature.home.presentation.di

import com.example.feature.home.presentation.ui.screen.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureHomePresentationModule = module {
    viewModelOf(::HomeScreenViewModel)
}