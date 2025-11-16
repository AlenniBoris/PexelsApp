package com.example.pexapp.data.source.dao

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pexapp.data.source.dao.model.PhotoEntityModelData


@Database(
    entities = [PhotoEntityModelData::class],
    version = 1,
    exportSchema = false
)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract val dao: FavouritesDatabaseDao

    companion object {
        fun get(
            apl: Application
        ) = Room.databaseBuilder(
            context = apl,
            klass = FavouritesDatabase::class.java,
            name = DatabaseValues.DATABASE_FILE
        ).build()
    }
}