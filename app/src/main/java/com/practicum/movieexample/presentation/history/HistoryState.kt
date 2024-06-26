package com.practicum.movieexample.presentation.history

import com.practicum.movieexample.domain.models.search.Movie

sealed interface HistoryState {
    object Loading: HistoryState
    data class Content(val movies: List<Movie>) : HistoryState
    data class Empty(val message: String): HistoryState
}