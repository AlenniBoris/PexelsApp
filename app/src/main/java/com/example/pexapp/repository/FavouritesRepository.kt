package com.example.pexapp.repository

import android.util.Log
import com.example.pexapp.database.FavouritesDatabase
import com.example.pexapp.entity.Photo

class FavouritesRepository(
    private val database: FavouritesDatabase
) {
    suspend fun addPhoto(favourite: Photo){
        try {
            database.dao.addPhoto(favourite)
        }catch (e: Exception){
            Log.e("FavouritesDatabase", e.message.toString())
        }
    }

    suspend fun deletePhoto(favourite: Photo){
        try {
            database.dao.deletePhoto(favourite)
        }catch (e: Exception){
            Log.e("FavouritesDatabase", e.message.toString())
        }
    }

    suspend fun getAllFavourites(): List<Photo>{
        val photos = try {
            database.dao.getAllFavourites()
        } catch (e: Exception){
            Log.e("FavouritesDatabase", e.message.toString())
            listOf()
        }
        return photos
    }

    suspend fun getFavouriteById(id: Int): Photo? {
        val photo = try {
            database.dao.getFavouriteById(id)
        }catch (e: Exception){
            Log.e("FavouritesDatabase", e.message.toString())
            null
        }

        return photo
    }

    suspend fun countById(id: Int): Int{
        val count = try {
            database.dao.countById(id)
        } catch (e: Exception){
            Log.e("FavouritesDatabase", e.message.toString())
            0
        }
        return count
    }
}