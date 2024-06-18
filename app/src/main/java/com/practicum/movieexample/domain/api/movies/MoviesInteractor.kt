package com.practicum.movieexample.domain.api.movies

import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.models.search.Movie
import com.practicum.movieexample.domain.models.cast.MovieCasts
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {
    fun searchMovies(expression: String) :Flow<Pair<List<Movie>?, String?>>
    fun searchMovie(expression: String): Flow<Pair<Response?, String?>>

    fun getMovieCasts(movieId: String): Flow<Pair<MovieCasts?, String?>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}