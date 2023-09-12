package com.example.giphy.repository

import com.example.giphy.model.GiphyImage
import com.example.giphy.model.PagingResult
import com.example.giphy.network.GiphyService
import javax.inject.Inject
import kotlin.math.ceil

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: GiphyService
) : SearchRepository {

    override suspend fun getSearchResults(
        searchKeyWord: String,
        offset: Int,
        amountOfGifs: Int
    ): PagingResult<List<GiphyImage>> {
        val response = searchApi.searchResult(
            offset = offset,
            limit = amountOfGifs,
            searchKeyWord = searchKeyWord,
        )

        val pagination = response.pagination

        return response.data.map {
            GiphyImage(
                id = it.id,
                title = it.title ?: "",
                url = it.images?.fixed_height?.url
            )
        }.let {
            PagingResult(
                data = it,
                totalGifs = pagination.total_count,
                currentPage = ceil(offset.toDouble() / 25.0).toInt()
            )
        }
    }
}