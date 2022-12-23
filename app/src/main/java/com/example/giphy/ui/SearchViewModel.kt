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
import com.example.giphy.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    companion object {
        const val PAGER_PAGE_SIZE = 25
    }

    @OptIn(FlowPreview::class)
    fun getDataFlow(keyWord: String): Flow<PagingData<GiphyImage>> = Pager(
        config = PagingConfig(pageSize = PAGER_PAGE_SIZE),
        initialKey = 0,
        pagingSourceFactory = {
            SearchSourceFactoryImpl(
                repository = searchRepository,
                keyWord = keyWord
            )

        }).flow.debounce(300).distinctUntilChanged().flowOn(Dispatchers.IO).cachedIn(viewModelScope)

}

class SearchSourceFactoryImpl(
    val keyWord: String,
    val repository: SearchRepository
) : PagingSource<Int, GiphyImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GiphyImage> {
        return try {
            val results = repository.getSearchResults(
                searchKeyWord = keyWord,
                offset = params.key ?: 1,
                count = params.loadSize
            )
            LoadResult.Page(
                data = results.data,
                prevKey = null,
                nextKey = if (results.data.isNotEmpty()) results.currentOffset + 1 else null
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GiphyImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}