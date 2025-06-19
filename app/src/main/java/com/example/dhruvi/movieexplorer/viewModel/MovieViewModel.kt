package com.example.dhruvi.movieexplorer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dhruvi.movieexplorer.data.Movie
import com.example.dhruvi.movieexplorer.network.NetworkMonitor
import com.example.dhruvi.movieexplorer.network.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) :  AndroidViewModel(application)  {
    private val networkMonitor = NetworkMonitor(application.applicationContext)

    val isConnected = networkMonitor.isConnected

    private var currentPage = 1
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val repository = MovieRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchMovies(resetPage: Boolean = true) {
        val query = _searchQuery.value.trim()

        if (query.isEmpty()) {
            _errorMessage.value = "Please enter a movie title."
            _movies.value = emptyList()
            return
        }

        if (resetPage) {
            currentPage = 1
            _movies.value = emptyList()
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.searchMovies(query, currentPage)
                if (result.isNotEmpty()) {
                    _movies.value += result
                    _errorMessage.value = ""
                    currentPage++
                } else if (_movies.value.isEmpty()) {
                    _errorMessage.value = "No results found."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load movies. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
