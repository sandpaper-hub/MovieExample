package com.practicum.movieexample.domain.api.movies

import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.models.search.Movie
import com.practicum.movieexample.domain.models.cast.MovieCasts
import com.practicum.movieexample.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun searchMovie(expression: String): Resource<Response>

    fun searchMovieCasts(expression: String): Resource<MovieCasts>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}