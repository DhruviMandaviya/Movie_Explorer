package com.example.dhruvi.movieexplorer.network.repository
import com.example.dhruvi.movieexplorer.data.Movies
import com.example.dhruvi.movieexplorer.network.api.MovieApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {
    private val API_KEY = "c98b8dc1166cb4b46f1f6b49502e2281"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log request + response + URL
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val apiService: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    suspend fun searchMovies(query: String, page: Int): List<Movies> {
        val response = apiService.searchMovies(API_KEY, query, page)
        return response.results ?: emptyList()
    }

    suspend fun discoverMoviesByGenre(genreID: Int, page: Int): List<Movies> {
        val response = apiService.discoverMoviesByGenre(API_KEY, genreID, page)
        return response.results ?: emptyList()
    }

}
