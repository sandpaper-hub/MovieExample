package com.practicum.movieexample.domain.impl

import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.api.MoviesRepository
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when (val resource = repository.searchMovies(expression)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }

    override fun searchMovie(expression: String, consumer: MoviesInteractor.MovieDetailConsumer) {
        executor.execute {
            when(val resource = repository.searchMovie(expression)){
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
                is Resource.Error -> {
                    consumer.consume(resource.data, resource.message)
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