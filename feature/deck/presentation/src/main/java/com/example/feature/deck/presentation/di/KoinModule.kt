package com.example.feature.deck.presentation.di

import com.example.feature.deck.presentation.ui.screen.DeckScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureDeckPresentationModule = module {
    viewModelOf(::DeckScreenViewModel)
}