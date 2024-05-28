package com.practicum.movieexample.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.practicum.movieexample.data.dto.cast.MovieCastsRequest
import com.practicum.movieexample.data.dto.detail.MovieDetailRequest
import com.practicum.movieexample.data.dto.movies.MoviesSearchRequest
import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.data.dto.people.PeopleSearchRequest

class RetrofitNetworkClient(private val imdbService: ImdbApi, private val context: Context) :
    NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return when (dto) {
            is MoviesSearchRequest -> {
                val resp = imdbService.findMovies(dto.expression).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            is MovieDetailRequest -> {
                val resp = imdbService.getMovieDetails(dto.id).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            is MovieCastsRequest -> {
                val resp = imdbService.getFullCasts(dto.id).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            is PeopleSearchRequest -> {
                val resp = imdbService.findPeople(dto.expression).execute()
                val body = resp.body() ?: Response()
                body.apply { resultCode = resp.code() }
            }

            else -> {
                Response().apply { resultCode = 400 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            }
        }
        return false
    }
}