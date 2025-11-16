package com.example.pexapp.di

import com.example.pexapp.data.source.api.PhotoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun providePhotoApiService(): PhotoApiService = PhotoApiService.get()
}