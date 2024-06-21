package com.example.pexapp.repository

import android.util.Log
import com.example.pexapp.api.PhotoApiService
import com.example.pexapp.entity.CollectionsEntity
import com.example.pexapp.entity.Photo
import com.example.pexapp.entity.PhotoEntity
import com.example.pexapp.entity.asEntity
import com.example.pexapp.responses.Collections
import com.example.pexapp.responses.FeaturedCollectionsResponse
import com.example.pexapp.responses.asEntity

class PhotoRepository(private val photoApiService: PhotoApiService) {

    suspend fun getPhotoById(id: Int): PhotoEntity?{
        val photo = try {
            photoApiService.getPhotoById(id).asEntity()
        } catch (e: Exception){
            null
        }
        return photo
    }

    suspend fun getCuratedPhotosList(page: Int, per_page: Int): List<PhotoEntity>{
        val listOfPhotos = try {
            photoApiService.getCuratedPhotos(page, per_page)
                .photos
                .map { it.asEntity() }
        } catch (e: Exception){
            listOf()
        }
        return listOfPhotos
    }

    suspend fun getSearchedPhotosList(query: String, page: Int, per_page: Int): List<PhotoEntity>{
        val listOfPhotos = try {
            photoApiService.getSearchedPhotos(query, page, per_page)
                .photos
                .map { it.asEntity() }
        } catch (e: Exception){
            listOf()
        }
        return listOfPhotos
    }

    suspend fun getFeaturedCollectionsList(page: Int, per_page: Int): List<CollectionsEntity>{
        val listOfCollections = try {
            photoApiService.getFeaturedCollections(page, per_page).collections.asEntity()
        } catch (e: Exception){
            listOf()
        }
        return listOfCollections
    }
}