package com.practicum.movieexample.data

import android.util.Log
import com.practicum.movieexample.data.converters.MovieCastConverter
import com.practicum.movieexample.data.dto.Casts
import com.practicum.movieexample.data.dto.MovieCastsRequest
import com.practicum.movieexample.data.dto.MovieCastsResponse
import com.practicum.movieexample.data.dto.MovieDetailRequest
import com.practicum.movieexample.data.dto.MovieDetailResponse
import com.practicum.movieexample.data.dto.MoviesSearchRequest
import com.practicum.movieexample.data.dto.MoviesSearchResponse
import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.api.MoviesRepository
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.domain.models.MovieCasts
import com.practicum.movieexample.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter
) : MoviesRepository {
    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        it.id,
                        it.resultType,
                        it.image,
                        it.title,
                        it.description,
                        stored.contains(it.id)
                    )
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun searchMovie(expression: String): Resource<Response> {
        val response = networkClient.doRequest(MovieDetailRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success(response as MovieDetailResponse)
            }

            else -> Resource.Error("Ошибка сервера")
        }
    }

    override fun searchMovieCasts(expression: String): Resource<MovieCasts> {
        val response = networkClient.doRequest(MovieCastsRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success(
                    movieCastConverter.convert(response as MovieCastsResponse)
                )
            }

            else -> Resource.Error("Ошибка сервера")
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

}