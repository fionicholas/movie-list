package com.github.fionicholas.movielist.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.fionicholas.movielist.R
import com.github.fionicholas.movielist.data.response.Movie
import com.github.fionicholas.movielist.di.Injection
import com.github.fionicholas.movielist.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MainAdapter()

        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(
            MainViewModel::class.java
        )

        viewModel.movies.observe(this, Observer<List<Movie>> {
            adapter.setMovies(it)
        })

        rv_movies.layoutManager = GridLayoutManager(this, 2)
        rv_movies.setHasFixedSize(true)
        rv_movies.adapter = adapter

        setupViewModel()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(
            MainViewModel::class.java
        )
        viewModel.movies.observe(this, renderMovies)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)

    }


    private val renderMovies = Observer<List<Movie>> {
        adapter.setMovies(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        progress_bar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v("TAG", "onMessageError $it")
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v("TAG", "emptyListObserver $it")
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMovies()
    }

}
