package com.practicum.movieexample.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.practicum.movieexample.data.NetworkClient
import com.practicum.movieexample.data.dto.MovieDetailRequest
import com.practicum.movieexample.data.dto.MovieDetailResponse
import com.practicum.movieexample.data.dto.MoviesSearchRequest
import com.practicum.movieexample.data.dto.Response

class RetrofitNetworkClient(private val imdbService: ImdbApi, private val context: Context) :
    NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        Log.d("EXAMPLE123", dto.toString())
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