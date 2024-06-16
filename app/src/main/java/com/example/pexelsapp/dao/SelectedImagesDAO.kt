package com.example.pexelsapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsapp.entities.SelectedImage
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SelectedImagesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addSelectedImage(selectedImageEntity: SelectedImage)

    @Query("SELECT * FROM 'selected-table'")
    abstract  fun getAllSelectedImages(): Flow<List<SelectedImage>>

    @Delete
    abstract suspend fun deleteSelectedImage(selectedImageEntity: SelectedImage)

    @Query("SELECT * FROM 'selected-table' WHERE id=:id")
    abstract fun getASelectedImageById(id: Long): Flow<SelectedImage>

}