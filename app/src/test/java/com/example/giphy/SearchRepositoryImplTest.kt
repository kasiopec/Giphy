package com.example.giphy

import com.example.giphy.Constants.CALL_LIMIT
import com.example.giphy.Constants.KEYWORD
import com.example.giphy.Constants.OFFSET
import com.example.giphy.Constants.TITLE
import com.example.giphy.Constants.TOTAL_COUNT
import com.example.giphy.model.GiphyImage
import com.example.giphy.model.PagingResult
import com.example.giphy.network.GiphyService
import com.example.giphy.network.model.Gif
import com.example.giphy.network.model.Pagination
import com.example.giphy.network.model.SearchResponse
import com.example.giphy.repository.SearchRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.math.ceil


class SearchRepositoryImplTest {
    private lateinit var searchRepository: SearchRepositoryImpl
    private val searchApi: GiphyService = mockk()

    private val listOfGifs = mockGifs()

    @Before
    fun setup() {
        searchRepository = SearchRepositoryImpl(
            searchApi = searchApi
        )
        coEvery {
            searchApi.searchResult(
                searchKeyWord = KEYWORD,
                offset = OFFSET,
                limit = CALL_LIMIT
            )
        } returns SearchResponse(
            data = listOfGifs,
            pagination = Pagination(
                count = 20,
                offset = OFFSET,
                total_count = TOTAL_COUNT
            )
        )
    }

    @Test
    fun `proper pagingResult is returned when api endpoint is called`() {
        val expectedData = PagingResult(
            data = listOf(
                GiphyImage(
                    id = 1.toString(),
                    title = TITLE,
                    url = null
                ),
                GiphyImage(
                    id = 2.toString(),
                    title = TITLE,
                    url = null
                ),
                GiphyImage(
                    id = 3.toString(),
                    title = TITLE,
                    url = null
                ),
                GiphyImage(
                    id = 4.toString(),
                    title = TITLE,
                    url = null
                ),
                GiphyImage(
                    id = 5.toString(),
                    title = TITLE,
                    url = null
                )
            ),
            currentPage = ceil(OFFSET.toDouble() / 25.0).toInt(),
            totalGifs = TOTAL_COUNT
        )
        runBlocking {
            val result = searchRepository.getSearchResults(
                searchKeyWord = KEYWORD,
                offset = OFFSET,
                amountOfGifs = CALL_LIMIT
            )
            assertEquals(expectedData, result)
        }
    }

    private fun mockGifs(): List<Gif> {
        val gif = Gif(
            analytics = null,
            analytics_response_payload = null,
            bitly_gif_url = null,
            bitly_url = null,
            content_url = null,
            embed_url = null,
            id = null,
            images = null,
            import_datetime = null,
            is_sticker = null,
            rating = null,
            slug = null,
            source = null,
            source_post_url = null,
            source_tld = null,
            title = TITLE,
            trending_datetime = null,
            type = null,
            url = "url",
            user = null,
            username = null
        )
        val gifList = mutableListOf<Gif>()
        for (i in 1..5) {
            gifList.add(
                gif.copy(
                    id = "$i",
                )
            )
        }
        return gifList
    }
}