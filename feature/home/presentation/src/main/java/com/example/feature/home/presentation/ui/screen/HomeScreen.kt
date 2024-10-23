package com.example.feature.home.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.common.designsystem.R
import com.example.common.designsystem.dimens.Dimens.big
import com.example.common.designsystem.dimens.Dimens.default
import com.example.common.designsystem.dimens.Dimens.huge
import com.example.common.designsystem.dimens.Dimens.medium
import com.example.common.designsystem.dimens.responsiveSp
import com.example.common.designsystem.theme.backgroundGradientColor
import com.example.common.designsystem.theme.designSystemThemePalette
import com.example.common.designsystem.theme.mavenProFontFamily
import com.example.common.presentation.components.ImageCard
import com.example.feature.home.presentation.ui.component.CustomDialog
import kotlinx.coroutines.flow.Flow

@Composable
internal fun HomeScreen(
    state: HomeScreenContract.State,
    effect: Flow<HomeScreenContract.Effect>,
    onEvent: (HomeScreenContract.Event) -> Unit,
    navigateToDeckScreen: (deckId: String) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        effect.collect {
            when (it) {
                is HomeScreenContract.Effect.NavigateToDeckScreen -> {
                    navigateToDeckScreen(it.deckId)
                }

                is HomeScreenContract.Effect.OpenConfirmationDialog -> {
                    showDialog = true
                }

                is HomeScreenContract.Effect.CloseConfirmationDialog -> {
                    showDialog = false
                }
            }
        }
    }

    HomeScreenContent(
        isLoadingScreen = state.isLoadingScreen,
        isLoadingButton = state.isLoadingButton,
        deckIds = state.deckIds,
        onEvent = onEvent,
        showDialog = showDialog
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    isLoadingScreen: Boolean,
    isLoadingButton: Boolean,
    deckIds: List<String>,
    onEvent: (HomeScreenContract.Event) -> Unit,
    showDialog: Boolean
) {
    val backCard = R.drawable.back_card_example

    if (showDialog) {
        CustomDialog(
            onCancelClicked = { onEvent(HomeScreenContract.Event.CloseConfirmationDialog) },
            onContinueClicked = { onEvent(HomeScreenContract.Event.DeleteDeckSelected) }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradientColor)
            .padding(top = default, start = default, end = default)
    ) {
        if (isLoadingScreen) {
            CircularProgressIndicator()
        } else {
            Text(
                text = "Decks",
                color = designSystemThemePalette.onSurfaceColor,
                fontSize = 18.sp.responsiveSp(),
                fontFamily = mavenProFontFamily,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = big)
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(4)
            ) {
                items(deckIds) { deckId ->
                    ImageCard(
                        image = backCard,
                        contentDescription = null,
                        modifier = Modifier
                            .combinedClickable(
                                onClick = {
                                    onEvent(HomeScreenContract.Event.NavigateToDeckScreen(deckId))
                                },
                                onLongClick = {
                                    onEvent(HomeScreenContract.Event.OpenConfirmationDialog(deckId))
                                }
                            )
                            .padding(default)
                    )
                }
            }

            Button(
                onClick = { onEvent(HomeScreenContract.Event.GetNewDeck) },
                enabled = !isLoadingButton,
                colors = ButtonDefaults.buttonColors(backgroundColor = designSystemThemePalette.primaryColor),
                shape = RoundedCornerShape(default),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = medium)
                    .fillMaxWidth()
                    .padding(horizontal = huge)
            ) {
                if (isLoadingButton) {
                    CircularProgressIndicator(modifier = Modifier.size(default))
                } else {
                    Text(
                        text = "Get new deck",
                        fontSize = 18.sp.responsiveSp(),
                        fontFamily = mavenProFontFamily
                    )
                }
            }
        }
    }
}