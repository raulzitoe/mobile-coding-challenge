package com.example.openlanephotogrid.data

import com.example.openlanephotogrid.data.api.UnsplashApi
import com.example.openlanephotogrid.data.model.PhotoModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val apiService: UnsplashApi
) {
    suspend fun getPhotos(page: Int): Result<List<PhotoModel>?> {
        return try {
            val response = apiService.getPhotos(page)

            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("Failed to fetch photos"))
            }
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            return Result.failure(e)
        }
    }
}
