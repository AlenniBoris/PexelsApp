package com.example.pexapp.api

import com.example.pexapp.api.ApiClient.apiService
import com.example.pexapp.utils.Constants.API_KEY
import com.example.pexapp.utils.Constants.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PhotoApiService::class.java)
}

fun main() {
    val apiKey = API_KEY
    val query = "house"
    val per_page = 30
    val page = 1

    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getSearchedPhotos(query, per_page, page)

            println(response)
            response.photos.forEach { photo ->
                println("c - Photo ID: ${photo.id}, Photographer: ${photo.photographer}, Photo: ${photo.url}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }

    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getFeaturedCollections(5,1)

            println(response)
            response.collections.forEach {
                println(it.title)
            }

        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }


    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getCuratedPhotos(per_page, page)

            println(response)
            response.photos.forEach { photo ->
                println("c - Photo ID: ${photo.id}, Photographer: ${photo.photographer}, Photo: ${photo.url}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }

    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getPhotoById(1029599)

            println(response)

        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }


}