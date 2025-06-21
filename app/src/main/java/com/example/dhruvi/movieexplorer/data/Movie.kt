package com.example.dhruvi.movieexplorer.data

//data class Movie(
//    val Title: String,
//    val Year: String,
//    val imdbID: String,
//    val Type: String,
//    val Poster: String
//)

//data class MovieResponse(
//    val Search: List<Movie>?,
//    val totalResults: String?,
//    val Response: String,
//    val Error: String? = null
//)

data class MovieResponse(
    val page: Int,
    val results: List<Movies>,
    val total_pages: Int,
    val total_results: Int
)

data class Movies(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val genre_ids: List<Int>,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val original_language: String,
    val video: Boolean,
    val adult: Boolean
)
