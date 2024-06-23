package com.example.pexapp.api

import com.example.pexapp.entity.Photo
import com.example.pexapp.responses.FeaturedCollectionsResponse
import com.example.pexapp.responses.PhotosResponse
import com.example.pexapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApiService {

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: Int
    ): Photo

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("curated")
    suspend fun getCuratedPhotos(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PhotosResponse

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("search")
    suspend fun getSearchedPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PhotosResponse

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): FeaturedCollectionsResponse

}