package com.example.giphy.di

import com.example.giphy.repository.SearchRepository
import com.example.giphy.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun searchRepository(repository: SearchRepositoryImpl): SearchRepository
}