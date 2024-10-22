package com.example.common.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ImageCard(image: Any?, contentDescription: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = image,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .then(modifier)
    )
}