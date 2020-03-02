package com.github.fionicholas.movielist.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.fionicholas.movielist.BuildConfig.URL_POSTER
import com.github.fionicholas.movielist.R
import com.github.fionicholas.movielist.data.response.Movie
import com.github.fionicholas.movielist.di.Injection
import com.github.fionicholas.movielist.viewmodel.ViewModelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailMovieViewModel

    companion object {
        const val TITLE_MOVIE = "title_movie"
        const val ID_MOVIE = "id_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.transparent)
        )

        val extras = intent.extras
        if (extras != null) {

            val moviesid = extras.getString(ID_MOVIE)
            val moviesTitle = extras.getString(TITLE_MOVIE)
            supportActionBar?.title = moviesTitle

            viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(
                DetailMovieViewModel::class.java)
            if (moviesid != null) {
                viewModel.setSelectedMovie(moviesid.toString())

                viewModel.movies.observe(this, Observer<Movie> { detail ->
                    detail?.let { populateMovies(it) }
                })

            }
        }

        setupViewModel()

    }

    private fun populateMovies(movies: Movie) {

        tv_title_detail.text = movies.title
        tv_release_detail.text = movies.release_date
        tv_rating_detail.text = movies.vote_average.toString()
        tv_overview_detail.text = movies.overview
        tv_language_detail.text = movies.original_language

        Glide.with(this)
            .load(URL_POSTER + movies.poster_path)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_poster_detail)
    }


    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(
            DetailMovieViewModel::class.java)
        viewModel.movies.observe(this,renderMovies)

        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.isEmptyList.observe(this,emptyListObserver)

    }


    private val renderMovies= Observer<Movie> {
        populateMovies(it)
    }


    private val onMessageErrorObserver= Observer<Any> {
        Log.v("TAG", "onMessageError $it")
    }

    private val emptyListObserver= Observer<Boolean> {
        Log.v("TAG", "emptyListObserver $it")
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDetailMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if (item?.getItemId() === android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
