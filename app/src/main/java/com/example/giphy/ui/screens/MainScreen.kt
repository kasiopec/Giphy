package com.example.giphy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.giphy.model.GiphyImage
import com.example.giphy.ui.SearchViewModel
import com.example.giphy.ui.components.ImageList
import com.example.giphy.ui.components.TextMediumRegular
import com.example.giphy.ui.components.VerticalSpacer
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber


@Composable
fun MainScreen(
    viewModel: SearchViewModel
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val images = remember {
        mutableStateOf(emptyFlow<PagingData<GiphyImage>>())
    }
    Timber.d("MainScreen recomposed")

    LaunchedEffect(key1 = textState.value.text) {
        images.value = viewModel.getDataFlow(textState.value.text)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(height = 10.dp)

        OutlinedTextField(
            value = textState.value,
            interactionSource = interactionSource,
            label = {
                Text(text = "Search")
            },
            placeholder = {
                Text(
                    text = "Search for gifs",
                    color = MaterialTheme.colors.onBackground
                )
            },
            onValueChange = { value ->
                textState.value = value
            },
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .focusRequester(focusRequester)
                .padding(horizontal = 8.dp),
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 18.sp),
            singleLine = true,
        )

        VerticalSpacer(height = 10.dp)

        TextMediumRegular(
            text = "Scroll for more",
            color = MaterialTheme.colors.onBackground
        )
        VerticalSpacer(height = 10.dp)

        ImageList(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    })
            },
            images = images.value.collectAsLazyPagingItems()
        )
    }
}

