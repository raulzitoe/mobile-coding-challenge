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
import javax.inject.Inject

@HiltViewModel
class PhotoGridViewModel @Inject constructor(
    photoRepository: PhotoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(
        PhotoGridScreenState(
            photosFlow = photoRepository.getPhotosFlow().map {
                pagingData -> pagingData.map { rowItems ->
                    rowItems.map { it.toPhotoUIModel() }
                }
            }.cachedIn(viewModelScope)
        )
    )
    val state = _state.asStateFlow()
}
