package xyz.teamgravity.shoppinglisttest.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.teamgravity.shoppinglisttest.BuildConfig
import xyz.teamgravity.shoppinglisttest.model.ImageResponseModel

interface PixabayApi {

    @GET("/api/")
    suspend fun searchImage(
        @Query("q") query: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponseModel>
}