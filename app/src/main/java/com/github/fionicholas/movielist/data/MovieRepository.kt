package com.github.fionicholas.movielist.data

import android.util.Log
import com.github.fionicholas.movielist.BuildConfig.API_KEY
import com.github.fionicholas.movielist.data.response.Movie
import com.github.fionicholas.movielist.data.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository : MovieDataSource {

    private var call: Call<MovieResponse>? = null
    private var call2: Call<Movie>? = null

    override fun retrieveMovie(callback: OperationCallback) {
        call = ApiService.build()?.movies(API_KEY, "en-US", "1")
        call?.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                response?.body()?.let {
                    if (response.isSuccessful) {
                        Log.v("TAG", "data ${it.results}")
                        callback.onSuccess(it.results)
                    } else {
                        callback.onError("Error")
                    }
                }
            }
        })
    }


    override fun retrieveDetailMovie(callback: OperationCallback, id_movie: String) {
        call2 = ApiService.build()?.detailMovies(id_movie, API_KEY, "en-US")
        Log.v("mdmsdmds", id_movie)
        call2?.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        Log.v("mdmsdmds", "data ${it.title}")
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Error")
                    }
                }
            }
        })
    }


    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }

}
