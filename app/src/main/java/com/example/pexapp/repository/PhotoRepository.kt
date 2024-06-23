package com.example.pexapp.repository

import android.util.Log
import com.example.pexapp.api.PhotoApiService
import com.example.pexapp.entity.Collections
import com.example.pexapp.entity.Photo

class PhotoRepository(private val photoApiService: PhotoApiService) {

    suspend fun getPhotoById(id: Int): Photo?{
        val photo = try {
            photoApiService.getPhotoById(id)
        } catch (e: Exception){
            Log.e("PhotoRepository", e.message.toString())
            null
        }
        return photo
    }

    suspend fun getCuratedPhotosList(page: Int, per_page: Int): List<Photo>{
        val listOfPhotos = try {
            photoApiService.getCuratedPhotos(page, per_page)
                .photos
        } catch (e: Exception){
            Log.e("PhotoRepository", e.message.toString())
            listOf()
        }
        return listOfPhotos
    }

    suspend fun getSearchedPhotosList(query: String, page: Int, per_page: Int): List<Photo>{
        val listOfPhotos = try {
            photoApiService.getSearchedPhotos(query, page, per_page)
                .photos
        } catch (e: Exception){
            Log.e("PhotoRepository", e.message.toString())
            listOf()
        }
        return listOfPhotos
    }

    suspend fun getFeaturedCollectionsList(page: Int, per_page: Int): List<Collections>{
        val listOfCollections = try {
            photoApiService.getFeaturedCollections(page, per_page).collections
        } catch (e: Exception){
            Log.e("PhotoRepository featured", e.message.toString())
            listOf()
        }
        return listOfCollections
    }
}