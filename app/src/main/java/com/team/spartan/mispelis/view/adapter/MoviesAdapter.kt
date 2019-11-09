package com.team.spartan.mispelis.view.adapter

import android.os.Handler
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.team.spartan.mispelis.R
import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.data.net.ServiceConstants
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_movie.view.*
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat


class MoviesAdapter(private var movies: List<Movie>):
    RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    val publishSubject: PublishSubject<Movie> = PublishSubject.create<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.team.spartan.mispelis.R.layout.item_movie, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) = holder.bind(movies[position], position)

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun getViewClickObservable(): Observable<Movie> {
        return publishSubject
    }

    inner class MovieHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, position: Int) = with(view) {
            val favoriteAnimation = AnimationUtils.loadAnimation(context,R.anim.favorite_animation)
            checkFavoriteImageView.visibility = View.GONE
            view.isClickable = true
            val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent?): Boolean {

                    checkFavoriteImageView.visibility = View.VISIBLE
                    checkFavoriteImageView.animation = favoriteAnimation
                    Handler().postDelayed({
                        checkFavoriteImageView.visibility = View.GONE
                    }, 600)


                    publishSubject.onNext(movies[position])
                    return true
                }
            })
            view.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }

            if (movie.posterPath != null) {
                Picasso.get().load(ServiceConstants.BASE_IMAGE_URL.value + movie.posterPath).placeholder(R.drawable.placeholder).into(movieImageView)
            } else {
                movieImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.placeholder, null))
            }
        }
    }
}