package com.example.pexelsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.entities.SelectedImage
import com.example.pexelsapp.graph.Graph
import com.example.pexelsapp.repository.SelectedImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SelectedImagesViewModel(
    private val selectedImagesRepository: SelectedImagesRepository = Graph.selectedImagesRepository
) : ViewModel(){

    lateinit var getAllSelectedImages: Flow<List<SelectedImage>>

    init{
        viewModelScope.launch {
            getAllSelectedImages = selectedImagesRepository.getSelectedImages()
        }
    }

    fun addSelectedImage(selectedImage: SelectedImage){
        viewModelScope.launch(Dispatchers.IO) {
            selectedImagesRepository.addSelectedImage(selectedImage = selectedImage)
        }
    }

    fun getASelectedImageById(id: Long) = selectedImagesRepository.getASelectedImageById(id = id)

    fun deleteSelectedImage(selectedImage: SelectedImage){
        viewModelScope.launch(Dispatchers.IO){
            selectedImagesRepository.deleteSelectedImage(selectedImage = selectedImage)
        }
    }

}