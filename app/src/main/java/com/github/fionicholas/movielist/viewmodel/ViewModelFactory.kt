package com.github.fionicholas.movielist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.fionicholas.movielist.data.MovieDataSource
import com.github.fionicholas.movielist.ui.detail.DetailMovieViewModel
import com.github.fionicholas.movielist.ui.main.MainViewModel

class ViewModelFactory (private val repository: MovieDataSource): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(
                    repository
                ) as T
            }

            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(
                    repository
                ) as T
            }


            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}