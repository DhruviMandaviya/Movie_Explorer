package com.example.dhruvi.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.dhruvi.movieexplorer.viewModel.MovieViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dhruvi.movieexplorer.ui.MovieExplorerApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Allow drawing behind the status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Change status bar icon/text color to dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setContent {
            val movieViewModel: MovieViewModel = viewModel()
            MovieExplorerApp(viewModel = movieViewModel)
        }
    }
}

