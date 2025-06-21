package com.example.dhruvi.movieexplorer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dhruvi.movieexplorer.data.Movies
import com.example.dhruvi.movieexplorer.viewModel.MovieViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.dhruvi.movieexplorer.data.Genre

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieExplorerApp(viewModel: MovieViewModel) {
    val query = viewModel.searchQuery.collectAsState().value
    val movies = viewModel.movies.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val listState = rememberLazyListState()
    val isLoading = viewModel.isLoading.collectAsState().value
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isConnected = viewModel.isConnected.collectAsState().value
    var selectedGenre by remember { mutableStateOf<Genre?>(null) }

    val movieGenres = listOf(
        Genre(28, "Action"),
        Genre(12, "Adventure"),
        Genre(16, "Animation"),
        Genre(35, "Comedy"),
        Genre(80, "Crime"),
        Genre(99, "Documentary"),
        Genre(18, "Drama"),
        Genre(10751, "Family"),
        Genre(14, "Fantasy"),
        Genre(36, "History"),
        Genre(27, "Horror"),
        Genre(10402, "Music"),
        Genre(9648, "Mystery"),
        Genre(10749, "Romance"),
        Genre(878, "Sci-Fi"),
        Genre(10770, "TV Movie"),
        Genre(53, "Thriller"),
        Genre(10752, "War"),
        Genre(37, "Western")
    )

    LaunchedEffect(Unit) {
        if (viewModel.selectedGenreId.value == null) {
            val defaultGenre = movieGenres.first()
            selectedGenre = defaultGenre
            viewModel.searchByGenre(defaultGenre.id)
        }
    }

    if (!isConnected) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = { Text("No Internet Connection") },
            text = { Text("Please turn on Wi-Fi or mobile data.") }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.9f))
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Text(
            text = "Movie Explorer",
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
            color = Color.White.copy(alpha = 0.8f),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search TextField
        TextField(
            value = query,
            maxLines = 1,
            onValueChange = {
                viewModel.onSearchQueryChange(it)
                selectedGenre = null
            },
            placeholder = { Text("Enter a movie name") },
            trailingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Gray.copy(alpha = 0.8f),
                focusedTextColor = Color.White.copy(alpha = 0.8f),
                unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                cursorColor = Color.Black.copy(alpha = 0.8f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.clearGenreSelection()
                focusManager.clearFocus(force = true) // 👈 Dismiss focus
                keyboardController?.hide() // 👈 Hide keyboard
                viewModel.searchMovies()
            })
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Genres Chips Row
        GenreChipRow(
            genres = movieGenres,
            selectedGenre = selectedGenre,
            onGenreSelected = {
                selectedGenre = it
                focusManager.clearFocus(force = true)
                viewModel.searchByGenre(it.id)
                viewModel.clearSearchQuery()
                viewModel.clearErrorMessage()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(movies) { movie ->
                    MovieCard(movie)
                }
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            // Trigger load more when scrolled to the end
            LaunchedEffect(
                key1 = listState.firstVisibleItemIndex,
                key2 = listState.layoutInfo.totalItemsCount
            ) {
                val lastVisibleItem =
                    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleItem >= totalItems - 1
                    && totalItems > 0
                    && !viewModel.isLoading.value
                ) {
                    viewModel.loadNextPage()
                }
            }
        }
    }
}


@Composable
fun GenreChipRow(
    genres: List<Genre>,
    selectedGenre: Genre?,
    onGenreSelected: (Genre) -> Unit
) {
    LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)) {
        items(genres) { genre ->
            GenreChip(
                genre = genre,
                isSelected = genre == selectedGenre,
                onClick = onGenreSelected
            )
        }
    }
}

@Composable
fun GenreChip(genre: Genre, isSelected: Boolean, onClick: (Genre) -> Unit) {
    Surface(
        shape = RoundedCornerShape(25.dp),
        color = if (isSelected) Color.White.copy(alpha = 0.9f) else Color.DarkGray,
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable {
                onClick(
                    genre,
                )
            }
    ) {
        Text(
            text = genre.name,
            color = if (isSelected) Color.DarkGray else Color.White.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun MovieCard(movie: Movies, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 5.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.4f))
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                contentDescription = movie.title ?: "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(7.dp)
            ) {
                Text(
                    text = movie.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = movie.overview ?: "N/A",
                    color = Color.Gray.copy(alpha = 0.8f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = movie.release_date?.takeIf { it.length >= 4 }?.take(4) ?: "N/A",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Yellow
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", movie.vote_average) + "/10",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

