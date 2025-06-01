package com.example.openlanephotogrid.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.openlanephotogrid.data.api.UnsplashApi
import com.example.openlanephotogrid.data.api.UnsplashApi.Companion.PAGE_SIZE
import com.example.openlanephotogrid.data.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val apiService: UnsplashApi
) {
    /**
     * Retrieves a [Flow] of [PagingData] for [PhotoModel] objects.
     *
     * This function uses the Paging 3 library to fetch photos from the Unsplash API
     * in a paginated manner.
     *
     * @return A [Flow] that emits [PagingData] containing [PhotoModel] items.
     */
    fun getPhotosFlow() : Flow<PagingData<PhotoModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { PhotosPagingSource(apiService) }
        ).flow
    }
}
