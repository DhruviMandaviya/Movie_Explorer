package com.example.dhruvi.movieexplorer.network.repository

import com.example.dhruvi.movieexplorer.data.Movie
import com.example.dhruvi.movieexplorer.network.api.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {
    private val apiKey = "43ac60e4"

    private val apiService: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    suspend fun searchMovies(query: String,page: Int = 1): List<Movie> {
        val response = apiService.searchMovies(apiKey, query,page)
        return response.Search ?: emptyList()
    }
}
