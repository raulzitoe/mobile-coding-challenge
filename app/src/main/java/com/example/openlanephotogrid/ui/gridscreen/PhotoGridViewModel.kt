package com.example.openlanephotogrid.ui.gridscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openlanephotogrid.data.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoGridViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            photoRepository.getPhotos(page = 1).onSuccess { photos ->
                val urls = photos?.joinToString("\n") { it.urls.small }
                Log.d("PhotoGridViewModel", "Photos: $urls")
            }.onFailure {

            }
        }
    }
}
