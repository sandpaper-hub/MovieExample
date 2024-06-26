package com.practicum.movieexample.data.repository

import com.practicum.movieexample.data.converters.MovieDbConverter
import com.practicum.movieexample.data.db.AppDatabase
import com.practicum.movieexample.data.db.entity.MovieEntity
import com.practicum.movieexample.domain.db.HistoryRepository
import com.practicum.movieexample.domain.models.search.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val movieDbConverter: MovieDbConverter
): HistoryRepository {
    override fun historyMovies(): Flow<List<Movie>> = flow{
        val movies = appDatabase.movieDao().getMovies()
        emit(convertFromMovieEntity(movies))
    }

    private fun convertFromMovieEntity(moies: List<MovieEntity>): List<Movie> {
        return moies.map { movie -> movieDbConverter.map(movie) }
    }
}