package com.practicum.movieexample.domain.api.movies

import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.models.search.Movie
import com.practicum.movieexample.domain.models.cast.MovieCasts
import com.practicum.movieexample.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>
    fun searchMovie(expression: String):Flow<Resource<Response>>

    fun searchMovieCasts(expression: String):Flow<Resource<MovieCasts>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}