package com.example.pexapp.data.source.dao.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourites_photos")
data class PhotoEntityModelData(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val photographer: String,
    @ColumnInfo(name = "photo_picture_medium_size_url")
    val photoPictureMediumSizeUrl: String,
    @ColumnInfo(name = "photo_picture_original_size_url")
    val photoPictureOriginalSizeUrl: String
)
