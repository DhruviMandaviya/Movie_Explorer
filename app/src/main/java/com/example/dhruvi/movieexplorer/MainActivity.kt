package com.example.dhruvi.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.dhruvi.movieexplorer.viewModel.MovieViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dhruvi.movieexplorer.ui.MovieExplorerApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val movieViewModel: MovieViewModel = viewModel()
            MovieExplorerApp(viewModel = movieViewModel)
        }
    }
}

