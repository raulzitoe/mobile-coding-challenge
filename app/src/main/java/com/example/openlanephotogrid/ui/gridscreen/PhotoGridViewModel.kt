package com.example.openlanephotogrid.ui.gridscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.openlanephotogrid.data.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PhotoGridViewModel @Inject constructor(
    photoRepository: PhotoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(
        PhotoGridScreenState(
            photosFlow = photoRepository.getPhotosFlow().map {
                pagingData -> pagingData.map { it.toPhotoUIModel() }
            }.cachedIn(viewModelScope),
            selectedPhotoId = null
        )
    )
    val state = _state.asStateFlow()

    fun onSelectedPhotoChanged(selectedPhotoId: String?) {
        _state.update { it.copy(selectedPhotoId = selectedPhotoId) }
    }
}
