package com.example.openlanephotogrid.ui.gridscreen

import androidx.paging.PagingData
import com.example.openlanephotogrid.ui.model.PhotoUIModel
import kotlinx.coroutines.flow.Flow

data class PhotoGridScreenState(
    val photosFlow: Flow<PagingData<PhotoUIModel>>,
    val selectedPhotoId: String?
)
