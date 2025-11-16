package com.example.pexapp.data.source.api

import com.andretietz.retrofit.ResponseCache
import com.example.pexapp.BuildConfig
import com.example.pexapp.data.source.api.model.FeaturedCollectionsResponseModelData
import com.example.pexapp.data.source.api.model.PhotoResponseModelData
import com.example.pexapp.data.source.api.model.PhotosResponseModelData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface PhotoApiService {


    @GET(ApiServiceValues.REQUEST_PHOTO_BY_ID)
    suspend fun getPhotoById(
        @Path(ApiServiceValues.FIELD_ID) id: Int
    ): PhotoResponseModelData


    @GET(ApiServiceValues.REQUEST_CURATED_PHOTOS)
    @ResponseCache(5, TimeUnit.MINUTES)
    suspend fun getCuratedPhotos(
        @Query(ApiServiceValues.FIELD_PAGE) page: Int = ApiServiceValues.BASE_PAGE_NUMBER,
        @Query(ApiServiceValues.FIELD_PER_PAGE) perPage: Int = ApiServiceValues.BASE_NUMBER_PER_PAGE
    ): PhotosResponseModelData


    @GET(ApiServiceValues.REQUEST_SEARCH)
    @ResponseCache(5, TimeUnit.MINUTES)
    suspend fun getSearchedPhotos(
        @Query(ApiServiceValues.FIELD_QUERY) query: String,
        @Query(ApiServiceValues.FIELD_PAGE) page: Int = ApiServiceValues.BASE_PAGE_NUMBER,
        @Query(ApiServiceValues.FIELD_PER_PAGE) perPage: Int = ApiServiceValues.BASE_NUMBER_PER_PAGE
    ): PhotosResponseModelData


    @GET(ApiServiceValues.REQUEST_FEATURED_COLLECTIONS)
    @ResponseCache(60, TimeUnit.MINUTES)
    suspend fun getFeaturedCollections(
        @Query(ApiServiceValues.FIELD_PAGE) page: Int = ApiServiceValues.BASE_PAGE_NUMBER,
        @Query(ApiServiceValues.FIELD_PER_PAGE) perPage: Int = ApiServiceValues.BASE_NUMBER_FEATURED_COLLECTIONS
    ): FeaturedCollectionsResponseModelData

    companion object {
        fun get() = Retrofit.Builder()
            .baseUrl(ApiServiceValues.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader(
                                name = ApiServiceValues.HEADER_AUTHORIZATION,
                                value = BuildConfig.PEXELS_API_KEY
                            )
                            .build()

                        chain.proceed(request)
                    }
                    .addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .build()
            .create(PhotoApiService::class.java)
    }
}