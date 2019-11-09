package com.team.spartan.mispelis.view.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.team.spartan.mispelis.R
import com.team.spartan.mispelis.data.interactor.MovieInteractor
import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.domain.MovieState
import com.team.spartan.mispelis.presenter.FavoritesPresenter
import com.team.spartan.mispelis.view.FavoritesView
import com.team.spartan.mispelis.view.adapter.FavoritesAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.toolbar_custom_layout.*
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : BaseActivity(), FavoritesView {

    private val toolbar: Toolbar by lazy { toolbar_view_view as Toolbar }

    override fun getToolbarInstance(): Toolbar? = toolbar

    private lateinit var presenter: FavoritesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setupComponents()
    }

    private fun setupComponents() {
        toolbar.title = getString(R.string.text_my_movies)
        favoritesRecyclerView.adapter = FavoritesAdapter(emptyList())
        presenter = FavoritesPresenter(MovieInteractor())
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun deleteFavoriteIntent(): Observable<Movie> {
        val observable = Observable.create<Movie> { emitter ->

            val helper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        val movie = (favoritesRecyclerView.adapter as FavoritesAdapter).getFavoriteAtPosition(position)
                        emitter.onNext(movie)
                    }
                }
            )
            helper.attachToRecyclerView(favoritesRecyclerView)
        }
        return observable
    }

    override fun displayFavoritesIntent(): Observable<Unit> = Observable.just(Unit)

    override fun render(state: MovieState) {
        when (state) {
            is MovieState.LoadState -> renderLoadState()
            is MovieState.DataState -> renderDataState(state)
            is MovieState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadState() {

    }

    private fun renderDataState(state: MovieState.DataState) {
        favoritesRecyclerView.apply {
            isEnabled = true
            (adapter as FavoritesAdapter).updateMovies(state.movies)
        }
    }

    private fun renderErrorState(state: MovieState.ErrorState) {

    }
}
