package com.example.giphy.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.giphy.model.GiphyImage
import com.example.giphy.util.Loading
import com.example.giphy.util.Error
import timber.log.Timber

@Composable
fun ImageList(
    modifier: Modifier = Modifier,
    images: LazyPagingItems<GiphyImage>
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                VerticalSpacer(height = 10.dp)
            }
//            when (val state = images.loadState.prepend) {
//                is LoadState.NotLoading -> {
//                }
//                is LoadState.Loading -> {
//                    Loading()
//                }
//                is LoadState.Error -> {
//                    Error(message = state.error.message ?: "")
//                }
//            }
            when (val state = images.loadState.refresh) {
                is LoadState.NotLoading -> Unit
                is LoadState.Loading -> {
                    Loading()
                }
                is LoadState.Error -> {
                    Error(message = state.error.message ?: "")
                }
            }

            items(
                count = images.itemCount
            ) {
                images.itemSnapshotList.items.forEach { image ->
                    Timber.d("images: $image")
                    Card(
                        title = image.title,
                        imageUrl = image.url
                    )
                    VerticalSpacer(height = 10.dp)
                }
            }

            when (val state = images.loadState.append) {
                is LoadState.NotLoading -> {
                }
                is LoadState.Loading -> {
                    Loading()
                }
                is LoadState.Error -> {
                    Error(message = state.error.message ?: "")
                }
            }
        }
    }
}