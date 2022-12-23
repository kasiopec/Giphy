package com.example.giphy.network

import com.example.giphy.network.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {


    @GET("gifs/search")
    suspend fun searchResult(
        @Query("q") searchKeyWord: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): SearchResponse

}