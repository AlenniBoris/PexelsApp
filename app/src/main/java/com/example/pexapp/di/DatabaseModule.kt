package com.example.pexapp.di

import android.app.Application
import android.content.Context
import com.example.pexapp.data.source.dao.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideFavouritesDatabase(
        @ApplicationContext application: Context
    ): FavouritesDatabase = FavouritesDatabase.get(
        apl = application as Application
    )
}