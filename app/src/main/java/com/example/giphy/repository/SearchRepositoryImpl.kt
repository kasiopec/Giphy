package com.example.giphy.repository

import com.example.giphy.model.GiphyImage
import com.example.giphy.model.PagingResult
import com.example.giphy.network.GiphyService
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchApi: GiphyService) : SearchRepository {

    override suspend fun getSearchResults(
        searchKeyWord: String,
        offset: Int,
        count: Int
    ): PagingResult<List<GiphyImage>> {
        val response = searchApi.searchResult(
            offset = offset,
            limit = count,
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
            val totalPage = pagination.total_count / pagination.count
            PagingResult(
                data = it,
                currentOffset = pagination.offset,
                nextOffset = (pagination.offset + 1).takeIf { next ->
                    next < totalPage
                }
            )
        }
    }
}