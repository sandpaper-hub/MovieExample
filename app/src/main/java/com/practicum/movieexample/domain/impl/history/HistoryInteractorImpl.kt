package com.practicum.movieexample.domain.impl.history

import com.practicum.movieexample.domain.db.HistoryInteractor
import com.practicum.movieexample.domain.db.HistoryRepository
import com.practicum.movieexample.domain.models.search.Movie
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(private val historyRepository: HistoryRepository) : HistoryInteractor {
    override fun historyMovies(): Flow<List<Movie>> {
        return historyRepository.historyMovies()
    }
}