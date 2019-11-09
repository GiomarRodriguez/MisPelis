package com.team.spartan.mispelis.presenter

import com.team.spartan.mispelis.data.interactor.MovieInteractor
import com.team.spartan.mispelis.domain.MovieState
import com.team.spartan.mispelis.view.FavoritesView
import com.team.spartan.mispelis.view.MoviesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesPresenter(private val movieInteractor: MovieInteractor) {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: MoviesView


    fun bind(view: MoviesView) {
        this.view = view
        compositeDisposable.add(observeFavoriteDisplay())
        compositeDisposable.add(observeAddLikeFavorite())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeAddLikeFavorite() = view.addLikeFavorite()
        .observeOn(Schedulers.io())
        .flatMap<MovieState> {
            movieInteractor.addMovie(it
            ) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }

    private fun observeFavoriteDisplay() = view.displayMoviesIntent()
        .flatMap<MovieState> { movieInteractor.fetchMovies() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.render(MovieState.LoadState) }
        .subscribe { view.render(it) }
}