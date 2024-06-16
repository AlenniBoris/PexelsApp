package com.example.pexelsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsapp.dao.SelectedImagesDAO
import com.example.pexelsapp.entities.SelectedImage


@Database(
    entities = [SelectedImage::class],
    version = 1,
    exportSchema = false
)
abstract class SelectedImagesDatabase : RoomDatabase() {

    abstract fun selectedImagesDAO() : SelectedImagesDAO

}