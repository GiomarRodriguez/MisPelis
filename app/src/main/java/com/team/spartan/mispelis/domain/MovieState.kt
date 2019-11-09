package com.team.spartan.mispelis.domain

import com.team.spartan.mispelis.data.model.Movie

sealed class MovieState {
    object LoadState : MovieState()
    data class DataState(val movies: List<Movie>) : MovieState()
    data class ErrorState(val message: String) : MovieState()
    data class AddState(val movie: Movie) : MovieState()
    object FinishState : MovieState()
}