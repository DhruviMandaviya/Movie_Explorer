package com.example.dhruvi.movieexplorer.network.api
import com.example.dhruvi.movieexplorer.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    // Search for movies by text query
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieResponse

    // Discover movies by genre ID
    @GET("discover/movie")
    suspend fun discoverMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreID: Int,
        @Query("page") page: Int = 1
    ): MovieResponse
}