package com.example.giphy.repository

import com.example.giphy.model.GiphyImage
import com.example.giphy.model.PagingResult

interface SearchRepository {
    suspend fun getSearchResults(searchKeyWord: String, offset: Int, amountOfGifs: Int): PagingResult<List<GiphyImage>>
}