package com.practicum.movieexample.data.network

import com.practicum.movieexample.data.dto.cast.MovieCastsResponse
import com.practicum.movieexample.data.dto.detail.MovieDetailResponse
import com.practicum.movieexample.data.dto.movies.MoviesSearchResponse
import com.practicum.movieexample.data.dto.people.PeopleSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApi {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    suspend fun findMovies(@Path("expression") expression: String): MoviesSearchResponse

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetailResponse

    @GET("/en/API/FullCast/k_zcuw1ytf/{id}")
    suspend fun getFullCasts(@Path("id") id: String): MovieCastsResponse

    @GET("en/API/SearchName/k_zcuw1ytf/{expression}")
    suspend fun findPeople(@Path("expression") expression: String) : PeopleSearchResponse
}