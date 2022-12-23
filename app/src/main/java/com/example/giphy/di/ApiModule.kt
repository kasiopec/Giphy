package com.example.giphy.di

import com.example.giphy.network.GiphyApi
import com.example.giphy.network.GiphyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    private val giphy: GiphyApi = GiphyApi()

    @Singleton
    @Provides
    fun searchApi(): GiphyService = giphy.buildService(GiphyService::class.java)
}