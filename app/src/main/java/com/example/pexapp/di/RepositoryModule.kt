package com.example.pexapp.di

import com.example.pexapp.data.repository.DatabaseRepositoryImpl
import com.example.pexapp.data.repository.WebDataRepositoryImpl
import com.example.pexapp.data.source.api.PhotoApiService
import com.example.pexapp.data.source.dao.FavouritesDatabase
import com.example.pexapp.domain.repository.IDatabaseRepository
import com.example.pexapp.domain.repository.IWebDataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindWebDataRepository(webDataRepositoryImpl: WebDataRepositoryImpl): IWebDataRepository

    @Binds
    abstract fun bindDatabaseRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): IDatabaseRepository
}