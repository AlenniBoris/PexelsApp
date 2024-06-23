package com.example.pexapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexapp.dao.FavouritesDatabaseDao
import com.example.pexapp.entity.Photo


@Database(
    entities = [Photo::class],
    version = 1,
    exportSchema = false
)
abstract class FavouritesDatabase: RoomDatabase(){
    abstract val dao: FavouritesDatabaseDao
}