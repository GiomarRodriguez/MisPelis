package com.team.spartan.mispelis.view

import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.domain.MovieState
import io.reactivex.Observable

interface FavoritesView {
    fun render(state: MovieState)
    fun displayFavoritesIntent(): Observable<Unit>
    fun deleteFavoriteIntent(): Observable<Movie>
}