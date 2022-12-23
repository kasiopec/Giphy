package com.example.giphy.model

import java.io.Serializable

data class GiphyImage(
    val id: String?,
    val title: String,
    val url: String?
) : Serializable
