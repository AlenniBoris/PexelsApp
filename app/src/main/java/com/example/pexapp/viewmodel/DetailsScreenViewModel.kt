package com.example.pexapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexapp.entity.PhotoEntity
import com.example.pexapp.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel(){
    val currentPhoto = MutableStateFlow<PhotoEntity?>(null)

    fun getPhotoFromPexelsById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val photo = photoRepository.getPhotoById(id)
                Log.d("getPhotoFromPexelsById", photo.toString())
                currentPhoto.emit(photo)
            }
        }
    }
}