package com.practicum.movieexample.data.network

import com.practicum.movieexample.data.dto.MovieCastsResponse
import com.practicum.movieexample.data.dto.MovieDetailResponse
import com.practicum.movieexample.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApi {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun findMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailResponse>

    @GET("/en/API/FullCast/k_zcuw1ytf/{id}")
    fun getFullCasts(@Path("id") id: String): Call<MovieCastsResponse>
}