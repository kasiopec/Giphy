package com.example.giphy.network.model

data class SearchResponse(
    val `data`: List<Gif>,
    val pagination: Pagination
)