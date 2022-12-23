package com.example.giphy.model

data class PagingResult<T>(
    val data: T,
    val currentOffset: Int,
    val nextOffset: Int?
)