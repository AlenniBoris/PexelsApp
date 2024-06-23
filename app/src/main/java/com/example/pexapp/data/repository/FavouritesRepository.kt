package com.example.pexapp.data.repository

import android.util.Log
import com.example.pexapp.data.mappers.asPhoto
import com.example.pexapp.data.mappers.asPhotoEntity
import com.example.pexapp.data.source.dao.FavouritesDatabase
import com.example.pexapp.data.model.Photo

class FavouritesRepository(
    private val database: FavouritesDatabase
) {
    suspend fun addPhoto(favourite: Photo) {
        try {
            database.dao.addPhoto(favourite.asPhotoEntity())
        }catch (e: Exception){
            Log.e("FavouritesDatabase-addPhoto", e.message.toString())
        }
    }

    suspend fun deletePhoto(favourite: Photo) {
        try {
            database.dao.deletePhoto(favourite.asPhotoEntity())
        }catch (e: Exception){
            Log.e("FavouritesDatabase-deletePhoto", e.message.toString())
        }
    }

    suspend fun getAllFavourites(): List<Photo> {
        val photos = try {
            database.dao.getAllFavourites().map { it.asPhoto() }
        } catch (e: Exception){
            Log.e("FavouritesDatabase-getAllFavourites", e.message.toString())
            emptyList()
        }
        return photos
    }

    suspend fun getFavouriteById(id: Int): Photo? {
        val photo = try {
            database.dao.getFavouriteById(id).asPhoto()
        }catch (e: Exception){
            Log.e("FavouritesDatabase-getFavouriteById", e.message.toString())
            null
        }

        return photo
    }

    suspend fun countById(id: Int): Int {
        val count = try {
            database.dao.countById(id)
        } catch (e: Exception){
            Log.e("FavouritesDatabase-countById", e.message.toString())
            0
        }
        return count
    }
}