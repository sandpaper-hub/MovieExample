package com.practicum.movieexample.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.practicum.movieexample.data.dto.cast.MovieCastsRequest
import com.practicum.movieexample.data.dto.detail.MovieDetailRequest
import com.practicum.movieexample.data.dto.movies.MoviesSearchRequest
import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.data.dto.people.PeopleSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(private val imdbService: ImdbApi, private val context: Context) :
    NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }

        if (dto !is PeopleSearchRequest && dto !is MoviesSearchRequest && dto !is MovieDetailRequest && dto !is MovieCastsRequest) {
            return Response().apply { resultCode = 400 }
        }

        return withContext(Dispatchers.IO) {
            try {
                var response: Response?
                when (dto) {
                    is PeopleSearchRequest -> {
                        response = imdbService.findPeople(dto.expression)
                        response.apply { resultCode = 200 }
                    }

                    is MoviesSearchRequest -> {
                        response = imdbService.findMovies(dto.expression)
                        response.apply { resultCode = 200 }
                    }

                    is MovieDetailRequest -> {
                        response = imdbService.getMovieDetails(dto.id)
                        response.apply { resultCode = 200 }
                    }

                    is MovieCastsRequest -> {
                        response = imdbService.getFullCasts(dto.id)
                        response.apply { resultCode = 200 }
                    }

                    else -> Response().apply { resultCode = 400 }
                }
            } catch (e: Throwable) {
                (Response().apply { resultCode = 500 })
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