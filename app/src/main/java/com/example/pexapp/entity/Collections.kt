package com.example.pexapp.entity

import java.io.Serializable


data class Collections(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int
) : Serializable
