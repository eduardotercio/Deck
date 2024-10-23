package com.example.deck.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.common.presentation.model.Route
import com.example.feature.deck.presentation.ui.route.DeckScreenRoute
import com.example.feature.home.presentation.ui.route.HomeScreenRoute

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Home
    ) {
        composable<Route.Home> {
            HomeScreenRoute(
                navController = navController
            )
        }
        composable<Route.Deck> {
            val arguments = it.toRoute<Route.Deck>()
            val deckId = arguments.deckId
            DeckScreenRoute(deckId = deckId)
        }
    }

}
