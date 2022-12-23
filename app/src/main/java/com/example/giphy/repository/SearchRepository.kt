package com.example.giphy.repository

import com.example.giphy.model.GiphyImage
import com.example.giphy.model.PagingResult

interface SearchRepository {
    suspend fun getSearchResults(searchKeyWord: String, offset: Int, count: Int): PagingResult<List<GiphyImage>>
}