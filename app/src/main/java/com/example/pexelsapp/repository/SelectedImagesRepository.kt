package com.example.pexelsapp.repository

import com.example.pexelsapp.dao.SelectedImagesDAO
import com.example.pexelsapp.entities.SelectedImage
import kotlinx.coroutines.flow.Flow

class SelectedImagesRepository(private val selectedImagesDAO: SelectedImagesDAO) {

    suspend fun addSelectedImage(selectedImage: SelectedImage) =
        selectedImagesDAO.addSelectedImage(selectedImage)

    fun getSelectedImages() : Flow<List<SelectedImage>> =
        selectedImagesDAO.getAllSelectedImages()

    fun getASelectedImageById(id: Long) : Flow<SelectedImage> =
        selectedImagesDAO.getASelectedImageById(id)

    suspend fun deleteSelectedImage(selectedImage: SelectedImage) =
        selectedImagesDAO.deleteSelectedImage(selectedImage)

}