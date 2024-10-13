package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardData(
    @SerialName("code")
    val code: String,

    @SerialName("image")
    val image: String,

    @SerialName("images")
    val imagePathTypes: List<ImagePathType>,

    @SerialName("value")
    val value: String,

    @SerialName("suit")
    val suit: String,
)
