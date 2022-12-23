package com.example.giphy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import com.example.giphy.ui.SearchViewModel
import com.example.giphy.ui.screens.MainScreen
import com.example.giphy.ui.theme.GiphyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scafoldState = rememberScaffoldState()

            GiphyTheme {
                Box(modifier = Modifier.fillMaxSize()){
                    Scaffold(
                        modifier = Modifier.navigationBarsPadding(),
                        scaffoldState = scafoldState,
                        content = { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                MainScreen(viewModel = viewModel)
                            }

                        }
                    )
                }
            }
        }
    }
}