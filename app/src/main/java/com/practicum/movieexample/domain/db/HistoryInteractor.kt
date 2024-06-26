package com.practicum.movieexample.domain.db

import com.practicum.movieexample.domain.models.search.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {
    fun historyMovies(): Flow<List<Movie>>
}