package com.example.pexelsapp.graph

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pexelsapp.database.SelectedImagesDatabase
import com.example.pexelsapp.repository.SelectedImagesRepository

object Graph {

    lateinit var database: SelectedImagesDatabase

    val selectedImagesRepository by lazy {
        SelectedImagesRepository(selectedImagesDAO = database.selectedImagesDAO())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(
            context,SelectedImagesDatabase::class.java, "selected.db"
        ).build()
    }

}