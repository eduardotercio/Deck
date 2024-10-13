package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val code: String,

    val image: String,

    val value: String,

    val suit: String,
)
