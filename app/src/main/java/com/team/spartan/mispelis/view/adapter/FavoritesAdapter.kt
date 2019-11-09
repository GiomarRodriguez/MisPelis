package com.team.spartan.mispelis.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.team.spartan.mispelis.R
import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.data.net.ServiceConstants
import kotlinx.android.synthetic.main.item_favorite.view.*


class FavoritesAdapter(private var movies: List<Movie>):
    RecyclerView.Adapter<FavoritesAdapter.FavoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) = holder.bind(movies[position])

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class FavoriteHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) = with(view) {
            favoriteTitleTextView.text = movie.title
            favoriteSynopsisTextView.text = movie.overview
            if (movie.posterPath != null) {
                Picasso.get().load(ServiceConstants.BASE_IMAGE_URL.value + movie.posterPath).placeholder(R.drawable.placeholder).into(favoriteImageView)
            } else {
                favoriteImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.placeholder, null))
            }
        }
    }

    fun getFavoriteAtPosition(position: Int) = movies[position]
}