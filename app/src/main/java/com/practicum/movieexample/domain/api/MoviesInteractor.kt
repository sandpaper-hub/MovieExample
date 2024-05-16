package com.practicum.movieexample.domain.api

import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)
    fun searchMovie(expression: String, consumer: MovieDetailConsumer)
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    interface MovieDetailConsumer{
        fun consume(foundMovie: Response?, errorMessage: String?)
    }
}