package com.github.fionicholas.movielist.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.fionicholas.movielist.BuildConfig.URL_POSTER
import com.github.fionicholas.movielist.R
import com.github.fionicholas.movielist.data.response.Movie
import com.github.fionicholas.movielist.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.items_movie.view.*
import java.util.ArrayList

class MainAdapter : RecyclerView.Adapter<MainAdapter.MovieViewHolder>() {
    private var listMovies = ArrayList<Movie>()

    fun setMovies(tvshows: List<Movie>?) {
        if (tvshows == null) return
        this.listMovies.clear()
        this.listMovies.addAll(tvshows)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = listMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int = listMovies.size


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                tv_title.text = movie.title
                tv_vote.text = movie.vote_average.toString()
                itemView.setOnClickListener {
                    val intent =
                        android.content.Intent(itemView.context, DetailMovieActivity::class.java)
                    //intent.putExtra(DetailMovieActivity.EXTRA_MOVIES, movie)
                    intent.putExtra(DetailMovieActivity.ID_MOVIE, movie.id.toString())
                    intent.putExtra(DetailMovieActivity.TITLE_MOVIE, movie.title)
                    Log.d("dssda", movie.id.toString())
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(URL_POSTER+ movie.poster_path)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
            }
        }
    }
}