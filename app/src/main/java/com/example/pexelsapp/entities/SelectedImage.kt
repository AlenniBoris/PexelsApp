package com.example.pexelsapp.entities

import androidx.compose.material3.dynamicLightColorScheme
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "selected-table")
data class SelectedImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "selected-image-imageId")
    val imageId: Long = 0L,
    @ColumnInfo(name = "selected-image-imageString")
    val imageString: String = ""
)

object Dummy{
    val list = listOf(
        SelectedImage(
            imageId = 1,
            imageString = "1"
        ),
        SelectedImage(
            imageId = 2,
            imageString = "2"
        )
    )
}