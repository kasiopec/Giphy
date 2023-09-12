package com.example.giphy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.giphy.model.GiphyImage
import com.example.giphy.repository.ApiConstants.GIPHY_API_LIMIT
import com.example.giphy.repository.ApiConstants.PAGER_PAGE_SIZE
import com.example.giphy.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    @OptIn(FlowPreview::class)
    fun getDataFlow(keyWord: String): Flow<PagingData<GiphyImage>> = Pager(
        config = PagingConfig(
            pageSize = PAGER_PAGE_SIZE,
            initialLoadSize = 10
        )
    ) {
        SearchSourceFactoryImpl(
            repository = searchRepository,
            keyWord = keyWord
        )
    }.flow.debounce(300).flowOn(Dispatchers.IO).cachedIn(viewModelScope)

}

class SearchSourceFactoryImpl(
    val keyWord: String,
    val repository: SearchRepository
) : PagingSource<Int, GiphyImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GiphyImage> {
        return try {
            val currentPage = params.key ?: 0
            val results = repository.getSearchResults(
                searchKeyWord = keyWord,
                offset = currentPage * PAGER_PAGE_SIZE,
                amountOfGifs = GIPHY_API_LIMIT
            )

            LoadResult.Page(
                data = results.data,
                prevKey = null,
                nextKey = if (results.data.isNotEmpty()) currentPage + 1 else null
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GiphyImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.minus(1) ?: anchorPage?.nextKey?.plus(1)
        }
    }

}