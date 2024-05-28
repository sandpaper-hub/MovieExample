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
    fun findMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailResponse>

    @GET("/en/API/FullCast/k_zcuw1ytf/{id}")
    fun getFullCasts(@Path("id") id: String): Call<MovieCastsResponse>

    @GET("en/API/SearchName/k_zcuw1ytf/{expression}")
    fun findPeople(@Path("expression") expression: String) : Call<PeopleSearchResponse>
}