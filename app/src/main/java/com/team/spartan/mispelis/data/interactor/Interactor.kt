package com.team.spartan.mispelis.data.interactor

import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.domain.MovieState
import io.reactivex.Observable

interface Interactor {
    fun fetchMovies(): Observable<MovieState>
    fun fetchFavorites(): Observable<MovieState>
    fun deleteMovie(movie: Movie): Observable<Unit>
    fun addMovie(movie: Movie): Observable<MovieState>
}