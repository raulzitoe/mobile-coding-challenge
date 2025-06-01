package com.example.openlanephotogrid.ui.gridscreen

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.openlanephotogrid.data.PhotoRepository
import com.example.openlanephotogrid.ui.model.PhotoUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PhotoGridViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(
        PhotoGridScreenState(
            photosFlow = getInitialPhotosFlow().cachedIn(viewModelScope),
            selectedPhotoId = null
        )
    )
    val state = _state.asStateFlow()

    fun onSelectedPhotoChanged(selectedPhotoId: String?) {
        _state.update { it.copy(selectedPhotoId = selectedPhotoId) }
    }

    private fun getInitialPhotosFlow(): Flow<PagingData<PhotoUIModel>> {
        return photoRepository.getPhotosFlow().map { pagingData ->
            pagingData.map { it.toPhotoUIModel() }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setNotCached() {
        _state.update {
            PhotoGridScreenState(
                photosFlow = getInitialPhotosFlow(),
                selectedPhotoId = null
            )
        }
    }
}
