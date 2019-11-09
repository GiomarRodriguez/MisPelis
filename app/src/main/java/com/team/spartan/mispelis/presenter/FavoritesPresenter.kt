package com.team.spartan.mispelis.presenter

import com.team.spartan.mispelis.data.interactor.MovieInteractor
import com.team.spartan.mispelis.domain.MovieState
import com.team.spartan.mispelis.view.FavoritesView
import com.team.spartan.mispelis.view.MoviesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoritesPresenter(private val movieInteractor: MovieInteractor) {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: FavoritesView


    fun bind(view: FavoritesView) {
        this.view = view
        compositeDisposable.add(observeMovieDisplay())
        compositeDisposable.add(observeDeleteFavoriteIntent())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeMovieDisplay() = view.displayFavoritesIntent()
        .flatMap<MovieState> { movieInteractor.fetchFavorites() }
        .startWith(MovieState.LoadState)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }

    private fun observeDeleteFavoriteIntent() = view.deleteFavoriteIntent()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .flatMap<Unit> { movieInteractor.deleteMovie(it) }
        .subscribe()
}