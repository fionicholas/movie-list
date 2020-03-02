package com.github.fionicholas.movielist.di

import com.github.fionicholas.movielist.data.MovieDataSource
import com.github.fionicholas.movielist.data.MovieRepository

object Injection {

    fun providerRepository(): MovieDataSource {
        return MovieRepository()
    }
}