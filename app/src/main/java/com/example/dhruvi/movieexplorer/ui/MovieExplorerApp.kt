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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dhruvi.movieexplorer.data.Movie
import com.example.dhruvi.movieexplorer.viewModel.MovieViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

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
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Text(text = "Movie Explorer", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = query,
            maxLines = 1,
            shape = RoundedCornerShape(5.dp),
            onValueChange = { viewModel.onSearchQueryChange(it) },
            placeholder = { Text("Enter a movie name", fontSize = 14.sp) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus() // 👈 Dismiss focus
                    keyboardController?.hide() // 👈 Hide keyboard
                    viewModel.searchMovies()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE3E3E3),       // Background color
                focusedIndicatorColor = Color.Transparent, // Remove focus indicator
                unfocusedIndicatorColor = Color.Transparent, // Remove unfocused indicator
            ),
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(8.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            shape = RectangleShape,
            onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                viewModel.searchMovies() },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text("SEARCH", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, fontSize = 18.sp, color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(movies) { movie ->
                    MovieCard(movie)
                }

                // 👇 Loading indicator inside LazyColumn
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
                if (lastVisibleItem >= totalItems - 1 && totalItems > 0 && !isLoading) {
                    viewModel.searchMovies(resetPage = false)
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = movie.Poster),
                contentDescription = movie.Title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = movie.Title, maxLines=2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = movie.Year,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
