package com.github.fionicholas.movielist.data

interface MovieDataSource {

    fun retrieveMovie(callback: OperationCallback)
    fun retrieveDetailMovie(callback: OperationCallback, id_movie: String)
    fun cancel()

}