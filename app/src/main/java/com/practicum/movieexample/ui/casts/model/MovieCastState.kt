package com.practicum.movieexample.ui.casts.model

import com.practicum.movieexample.domain.models.MovieCasts
import com.practicum.movieexample.presentation.movieCast.MovieCastRVItem
import com.practicum.movieexample.ui.casts.RVItem
import com.practicum.movieexample.ui.movies.model.MoviesState

sealed interface MovieCastState {
    data class Content(val fullTitle: String, val items: List<RVItem>) : MovieCastState
    data class Error(val message: String?) : MovieCastState
    object Loading : MovieCastState
}