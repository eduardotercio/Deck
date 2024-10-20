package com.example.feature.deck.presentation.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.feature.deck.presentation.ui.screen.DeckScreen
import com.example.feature.deck.presentation.ui.screen.DeckScreenContract
import com.example.feature.deck.presentation.ui.screen.DeckScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeckScreenRoute(
    deckId: String
) {
    val viewModel = koinViewModel<DeckScreenViewModel>()
    val state = viewModel.state.collectAsState().value
    val effect = viewModel.effect

    viewModel.setEvent(DeckScreenContract.Event.FetchDeck(deckId))

    DeckScreen(
        state = state,
        effect = effect,
        onEvent = { event ->
            viewModel.setEvent(event)
        }
    )
}