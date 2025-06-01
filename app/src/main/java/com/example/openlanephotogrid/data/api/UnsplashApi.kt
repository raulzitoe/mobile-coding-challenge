package com.example.openlanephotogrid.data.api

import com.example.openlanephotogrid.data.model.PhotoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {
    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_SIZE
    ): Response<List<PhotoModel>>

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        private const val CLIENT_ID = "Z4tjv-MY_ELYTUlP6Xz4DqrUcRLSNkv6ZNzbViApcH0"
        private const val PAGE_SIZE = 10
    }
}
