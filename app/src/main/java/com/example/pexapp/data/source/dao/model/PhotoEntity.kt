package com.example.pexapp.data.source.dao.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourites_photos")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @ColumnInfo(name = "photographer_url")
    val photographerUrl: String,
    @ColumnInfo(name = "photographer_id")
    val photographerId: Int,
    @ColumnInfo(name = "avg_color")
    val avgColor: String,
    @Embedded(prefix = "src_")
    val src: PhotoFeaturesEntity,
    val liked: Boolean,
    val alt: String
)
