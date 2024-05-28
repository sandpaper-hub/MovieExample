package com.practicum.movieexample.ui.casts.model

import com.practicum.movieexample.ui.casts.RVItem

sealed interface MovieCastState {
    data class Content(val fullTitle: String, val items: List<RVItem>) : MovieCastState
    data class Error(val message: String?) : MovieCastState
    object Loading : MovieCastState
}