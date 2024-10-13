package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagePathType(
    @SerialName("svg")
    val svgPath: String,

    @SerialName("png")
    val pngPath: String
)
