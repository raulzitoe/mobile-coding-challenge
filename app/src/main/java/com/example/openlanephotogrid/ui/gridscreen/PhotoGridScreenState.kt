package com.example.openlanephotogrid.ui.gridscreen

import androidx.paging.PagingData
import com.example.openlanephotogrid.ui.model.PhotoUIModel
import kotlinx.coroutines.flow.Flow

/**
 * Represents the state of the Photo Grid Screen.
 *
 * @property photosFlow A [Flow] of [PagingData] containing [PhotoUIModel] objects,
 * representing the paginated list of photos to be displayed in the grid.
 * @property selectedPhotoId The ID of the currently selected photo. This can be null
 * if no photo is selected.
 */
data class PhotoGridScreenState(
    val photosFlow: Flow<PagingData<PhotoUIModel>>,
    val selectedPhotoId: String?
)
