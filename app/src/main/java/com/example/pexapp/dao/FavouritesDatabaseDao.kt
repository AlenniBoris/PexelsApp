package com.example.pexapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexapp.entity.Photo

@Dao
interface FavouritesDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photo: Photo)

    @Delete
    suspend fun deletePhoto(photo: Photo)

    @Query("SELECT * FROM favourites_photos")
    suspend fun getAllFavourites(): List<Photo>

    @Query("SELECT * FROM favourites_photos WHERE id=:id")
    suspend fun getFavouriteById(id: Int): Photo

    @Query("SELECT COUNT(*) FROM favourites_photos WHERE id=:id")
    suspend fun countById(id: Int): Int

}