package com.example.dhruvi.movieexplorer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dhruvi.movieexplorer.data.Movies
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

    enum class SearchMode { NONE, TEXT, GENRE }
    private var searchMode = SearchMode.NONE

    private val repository = MovieRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _movies = MutableStateFlow<List<Movies>>(emptyList())
    val movies: StateFlow<List<Movies>> = _movies

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _selectedGenreId = MutableStateFlow<Int?>(null)
    val selectedGenreId: StateFlow<Int?> = _selectedGenreId

    fun clearGenreSelection() {
        _selectedGenreId.value = null
        searchMode = SearchMode.NONE
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        clearGenreSelection()
        searchMode = SearchMode.TEXT
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
    }

    fun clearErrorMessage() {
        _errorMessage.value = ""
    }

    fun loadNextPage() {
        if (_isLoading.value) return

        when (searchMode) {
            SearchMode.TEXT -> {
                if (_searchQuery.value.isNotBlank())
                    searchMovies(resetPage = false)
            }
            SearchMode.GENRE -> {
                selectedGenreId.value?.let { searchByGenre(it, resetPage = false) }
            }
            else -> Unit // no-op
        }
    }


    fun searchByGenre(genreID: Int, resetPage: Boolean=true ) {
        searchMode = SearchMode.GENRE
        _selectedGenreId.value = genreID

        if (resetPage) {
            currentPage = 1
            _movies.value = emptyList()
        }
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.discoverMoviesByGenre(genreID, currentPage)
                if (result.isNotEmpty()) {
                    _movies.value += result
                    _errorMessage.value = ""
                    currentPage++
                } else if (_movies.value.isEmpty()) {
                    _errorMessage.value = "No results found for this genre."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load genre movies. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchMovies(resetPage: Boolean = true) {
        val query = _searchQuery.value.trim()
        _selectedGenreId.value = null
        searchMode = SearchMode.TEXT

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
                e.printStackTrace()
                _errorMessage.value = "Failed to load movies. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
