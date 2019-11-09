package com.team.spartan.mispelis.view.activity

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.team.spartan.mispelis.R
import com.team.spartan.mispelis.data.interactor.MovieInteractor
import com.team.spartan.mispelis.data.model.Movie
import com.team.spartan.mispelis.domain.MovieState
import com.team.spartan.mispelis.presenter.MoviesPresenter
import com.team.spartan.mispelis.view.MoviesView
import com.team.spartan.mispelis.view.adapter.MoviesAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_custom_layout.*
import org.jetbrains.anko.startActivity


class MoviesActivity : BaseActivity(), MoviesView {

    private val toolbar: Toolbar by lazy { toolbar_view_view as Toolbar }

    override fun getToolbarInstance(): Toolbar? = toolbar

    private lateinit var presenter: MoviesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupComponents()
    }

    fun setupComponents() {
        toolbar.title = getString(R.string.text_popularity)
        moviesRecyclerView.layoutManager = GridLayoutManager(this,2)
        moviesRecyclerView.adapter = MoviesAdapter(emptyList())
        presenter = MoviesPresenter(MovieInteractor())
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun addLikeFavorite(): Observable<Movie> = (moviesRecyclerView.adapter as MoviesAdapter).getViewClickObservable()

    fun goToFavoritesActivity() = startActivity<FavoritesActivity>()

    override fun displayMoviesIntent():  Observable<Unit> = Observable.just(Unit)

    override fun render(state: MovieState) {
        when(state) {
            is MovieState.LoadState -> renderLoadState()
            is MovieState.DataState -> renderDataState(state)
            is MovieState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadState() {
        var d = 5
        var g = d + 9
    }

    private fun renderDataState(state: MovieState.DataState) {
        moviesRecyclerView.apply {
            (adapter as MoviesAdapter).updateMovies(state.movies)
        }
    }

    private fun renderErrorState(state: MovieState.ErrorState) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_navigation) {
            goToFavoritesActivity()
        }

        return super.onOptionsItemSelected(item)
    }
}
