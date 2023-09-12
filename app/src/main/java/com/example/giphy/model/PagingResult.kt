package com.example.giphy.model

data class PagingResult<T>(
    val data: T,
    val totalGifs: Int,
    val currentPage: Int
)