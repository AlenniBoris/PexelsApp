package com.example.pexapp.di

import com.example.pexapp.domain.util.IAppDispatchers
import com.example.pexapp.presentation.uikit.utils.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun provideImageLoader(
        dispatchers: IAppDispatchers
    ): ImageLoader = ImageLoader(dispatchers = dispatchers)
}