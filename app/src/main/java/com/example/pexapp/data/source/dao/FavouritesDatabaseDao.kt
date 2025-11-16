package com.example.pexapp.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexapp.data.source.dao.model.PhotoEntityModelData
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photoEntity: PhotoEntityModelData)

    @Delete
    suspend fun deletePhoto(photoEntity: PhotoEntityModelData)

    @Query("SELECT * FROM favourites_photos")
    fun getAllFavourites(): Flow<List<PhotoEntityModelData>>

    @Query("SELECT * FROM favourites_photos WHERE id=:id")
    suspend fun getFavouriteById(id: Int): PhotoEntityModelData

    @Query("SELECT COUNT(*) FROM favourites_photos WHERE id=:id")
    suspend fun countById(id: Int): Int

}