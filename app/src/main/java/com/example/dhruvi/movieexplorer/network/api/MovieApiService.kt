package com.example.dhruvi.movieexplorer.network.api
import com.example.dhruvi.movieexplorer.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String,
        @Query("page") page: Int = 1
    ): MovieResponse

}