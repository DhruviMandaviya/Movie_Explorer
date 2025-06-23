package com.example.dhruvi.movieexplorer.network.repository
import com.example.dhruvi.movieexplorer.data.Movies
import com.example.dhruvi.movieexplorer.network.api.MovieApi

class MovieRepository(
    private val apiService: MovieApi,
    // API key for TMDB (should be stored securely in real apps)
    private val API_KEY: String = "c98b8dc1166cb4b46f1f6b49502e2281"
) {
    // Fetch movies by search query and page
    suspend fun searchMovies(query: String, page: Int): List<Movies> {
        val response = apiService.searchMovies(API_KEY, query, page)
        return response.results ?: emptyList()
    }

    // Fetch movies by genre and page
    suspend fun discoverMoviesByGenre(genreID: Int, page: Int): List<Movies> {
        val response = apiService.discoverMoviesByGenre(API_KEY, genreID, page)
        return response.results ?: emptyList()
    }

}
