package com.practicum.movieexample.domain.impl.movies

import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.api.movies.MoviesInteractor
import com.practicum.movieexample.domain.api.movies.MoviesRepository
import com.practicum.movieexample.domain.models.cast.MovieCasts
import com.practicum.movieexample.domain.models.search.Movie
import com.practicum.movieexample.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>> {
        return repository.searchMovies(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun searchMovie(expression: String): Flow<Pair<Response?, String?>> {
        return repository.searchMovie(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun getMovieCasts(movieId: String): Flow<Pair<MovieCasts?, String?>> {
        return repository.searchMovieCasts(movieId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }


    override fun addMovieToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}