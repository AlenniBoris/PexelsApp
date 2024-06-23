package com.example.pexapp.di

import android.app.Application
import androidx.room.Room
import com.example.pexapp.data.source.api.PhotoApiService
import com.example.pexapp.data.source.dao.FavouritesDatabase
import com.example.pexapp.data.repository.FavouritesRepository
import com.example.pexapp.data.repository.PhotoRepository
import com.example.pexapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val HEADER_AUTHORIZATION = "Authorization"

    private const val DATABASE_FILE = "favourites-data.db"

    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(HEADER_AUTHORIZATION, Constants.API_KEY)
                .build()

            chain.proceed(request)
        }
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providePhotoApiService(okHttpClient: OkHttpClient) : PhotoApiService =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PhotoApiService::class.java)

    @Provides
    @Singleton
    fun providePhotoDataRepository(photoApiService: PhotoApiService): PhotoRepository =
        PhotoRepository(photoApiService)

    @Provides
    @Singleton
    fun provideFavouritesDatabase(application: Application): FavouritesDatabase =
        Room.databaseBuilder(
            application,
            FavouritesDatabase::class.java,
            DATABASE_FILE
        ).build()

    @Provides
    @Singleton
    fun providesFavouritesRepository(database: FavouritesDatabase): FavouritesRepository =
        FavouritesRepository(database)

}