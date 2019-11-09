package com.team.spartan.mispelis.data.interactor

import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.data.net.RetrofitClient
import com.team.spartan.mispelis.database
import com.team.spartan.mispelis.domain.MovieState
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MovieInteractor : Interactor {

    private val serviceData = RetrofitClient()
    private val localData = database.movieDao()

    override fun fetchMovies(): Observable<MovieState> =
        serviceData.fetchMovies()
            .observeOn(Schedulers.io())
            .map<MovieState> {
                it.results?.let {
                    MovieState.DataState(it)
                }
            }
            .onErrorReturn {
                MovieState.ErrorState("Error from fetch remote movies")
            }


    override fun fetchFavorites(): Observable<MovieState> =
        localData.getAll()
            .map<MovieState> { MovieState.DataState(it) }
            .onErrorReturn { MovieState.ErrorState("Error from fetch local movies") }

    override fun deleteMovie(movie: Movie): Observable<Unit> =
        localData.delete(movie)
            .toObservable()

    override fun addMovie(movie: Movie): Observable<MovieState> =
        localData.insert(movie)
            . map<MovieState> {
                MovieState.FinishState
            }.toObservable()
}