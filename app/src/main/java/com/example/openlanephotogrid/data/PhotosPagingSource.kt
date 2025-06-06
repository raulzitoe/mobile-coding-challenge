package com.example.openlanephotogrid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.openlanephotogrid.data.api.UnsplashApi
import com.example.openlanephotogrid.data.model.PhotoModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

class PhotosPagingSource(
    private val service: UnsplashApi,
) : PagingSource<Int, PhotoModel>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, PhotoModel> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = service.getPhotos(nextPageNumber)
            val data = response.body()

            return LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoModel>): Int? {
        return null
    }
}
