package com.github.fionicholas.movielist.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.fionicholas.movielist.data.MovieDataSource
import com.github.fionicholas.movielist.data.OperationCallback
import com.github.fionicholas.movielist.data.response.Movie

class DetailMovieViewModel (private val repository: MovieDataSource): ViewModel() {

    private lateinit var moviesId: String

    fun setSelectedMovie(moviesId: String) {
        this.moviesId = moviesId

    }

    private val _movies = MutableLiveData<Movie>()
    val movies: LiveData<Movie> = _movies

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList



    fun loadDetailMovie() {

        repository.retrieveDetailMovie(object : OperationCallback {
            override fun onError(obj: Any?) {
                _onMessageError.postValue(obj)
            }

            override fun onSuccess(obj: Any?) {

                if (obj != null && obj is Movie) {
                    if (obj == null) {
                        _isEmptyList.postValue(true)
                    } else {
                        _movies.value = obj
                    }
                }
            }
        },
            moviesId

        )
    }

}