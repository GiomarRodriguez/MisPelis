package com.team.spartan.mispelis.view

import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.domain.MovieState
import io.reactivex.Observable

interface MoviesView {
    fun render(state: MovieState)
    fun displayMoviesIntent(): Observable<Unit>
    fun addLikeFavorite(): Observable<Movie>
}