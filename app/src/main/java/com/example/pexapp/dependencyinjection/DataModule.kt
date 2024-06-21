package com.example.pexapp.dependencyinjection

import com.example.pexapp.PexelsApplication
import com.example.pexapp.api.PhotoApiService
import com.example.pexapp.repository.PhotoRepository
import com.example.pexapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providePhotoApiService() : PhotoApiService =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoApiService::class.java)

    @Provides
    @Singleton
    fun providePhotoDataRepository(photoApiService: PhotoApiService): PhotoRepository =
        PhotoRepository(photoApiService)

}