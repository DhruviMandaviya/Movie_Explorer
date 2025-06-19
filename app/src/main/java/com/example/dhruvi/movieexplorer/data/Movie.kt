package com.example.dhruvi.movieexplorer.data

data class Movie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)

data class MovieResponse(
    val Search: List<Movie>?,
    val totalResults: String?,
    val Response: String,
    val Error: String? = null
)
