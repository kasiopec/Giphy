package com.example.giphy.util

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun LazyListScope.Loading() {
    item {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

fun LazyListScope.Error(
    message: String
) {
    item {
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error
        )
    }
}