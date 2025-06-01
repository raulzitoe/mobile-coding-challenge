package com.example.openlanephotogrid.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.openlanephotogrid.data.api.UnsplashApi
import com.example.openlanephotogrid.data.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val apiService: UnsplashApi
) {
    fun getPhotosFlow() : Flow<PagingData<PhotoModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PhotosPagingSource(apiService) }
        ).flow
    }
}
