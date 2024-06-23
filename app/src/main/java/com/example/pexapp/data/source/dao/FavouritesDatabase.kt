package com.example.pexapp.data.source.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexapp.data.source.dao.model.PhotoEntity


@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavouritesDatabase: RoomDatabase(){
    abstract val dao: FavouritesDatabaseDao
}