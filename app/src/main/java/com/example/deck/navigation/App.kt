package com.example.deck.navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.common.designsystem.theme.DeckTheme
import com.example.hqsmarvel.presentation.navigation.NavGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    DeckTheme {
        val navController = rememberNavController()
        Scaffold {
            NavGraph(navController = navController)
        }
    }
}