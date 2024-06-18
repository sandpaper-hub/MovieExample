package com.practicum.movieexample.data.repository

import com.practicum.movieexample.data.storage.LocalStorage
import com.practicum.movieexample.data.network.NetworkClient
import com.practicum.movieexample.data.converters.MovieCastConverter
import com.practicum.movieexample.data.dto.cast.MovieCastsRequest
import com.practicum.movieexample.data.dto.cast.MovieCastsResponse
import com.practicum.movieexample.data.dto.detail.MovieDetailRequest
import com.practicum.movieexample.data.dto.detail.MovieDetailResponse
import com.practicum.movieexample.data.dto.movies.MoviesSearchRequest
import com.practicum.movieexample.data.dto.movies.MoviesSearchResponse
import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.api.movies.MoviesRepository
import com.practicum.movieexample.domain.models.search.Movie
import com.practicum.movieexample.domain.models.cast.MovieCasts
import com.practicum.movieexample.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter
) : MoviesRepository {
    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()
                emit(
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
                )
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun searchMovie(expression: String): Flow<Resource<Response>> = flow {
        val response = networkClient.doRequest(MovieDetailRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                emit(Resource.Success(response as MovieDetailResponse))
            }

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun searchMovieCasts(expression: String): Flow<Resource<MovieCasts>> = flow {
        val response = networkClient.doRequest(MovieCastsRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                emit(Resource.Success(movieCastConverter.convert(response as MovieCastsResponse)))
            }

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

}