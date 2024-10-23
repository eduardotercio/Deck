package com.example.feature.home.presentation.ui.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.presentation.model.Route
import com.example.feature.home.presentation.ui.screen.HomeScreen
import com.example.feature.home.presentation.ui.screen.HomeScreenContract
import com.example.feature.home.presentation.ui.screen.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoute(
    navController: NavController
) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val effect = viewModel.effect

    viewModel.setEvent(HomeScreenContract.Event.GetDeckIds)

    HomeScreen(
        state = state,
        effect = effect,
        onEvent = { event ->
            viewModel.setEvent(event)
        },
        navigateToDeckScreen = { deckId ->
            navController.navigate(Route.Deck(deckId = deckId))
        }
    )
}