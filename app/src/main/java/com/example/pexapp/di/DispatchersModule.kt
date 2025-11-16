package com.example.pexapp.di

import com.example.pexapp.domain.util.IAppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Singleton
    fun provideDispatchers(): IAppDispatchers = object : IAppDispatchers {
        override val IO: CoroutineDispatcher = Dispatchers.IO
        override val Default: CoroutineDispatcher = Dispatchers.Default
        override val Main: CoroutineDispatcher = Dispatchers.Main
    }
}