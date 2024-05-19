package com.practicum.movieexample.presentation.movieCast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.models.MovieCasts
import com.practicum.movieexample.ui.casts.model.MovieCastState

class MovieCastViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {
    private val liveData = MutableLiveData<MovieCastState>()
    fun observeState() = liveData

    init {
        liveData.postValue(MovieCastState.Loading)
        moviesInteractor.getMovieCasts(movieId, object : MoviesInteractor.MovieCastsConsumer {
            override fun consume(movieCast: MovieCasts?, errorMessage: String?) {
                if (movieCast != null) {
                    liveData.postValue(castToUiStateContent(movieCast))
                } else {
                    liveData.postValue(MovieCastState.Error(errorMessage))
                }
            }

        })
    }

    private fun castToUiStateContent(cast: MovieCasts): MovieCastState {
        // Строим список элементов RecyclerView
        val items = buildList<MovieCastRVItem> {
            // Если есть хотя бы один режиссёр, добавим заголовок
            if (cast.directors.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MovieCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один сценарист, добавим заголовок
            if (cast.writers.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MovieCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один актёр, добавим заголовок
            if (cast.actors.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MovieCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один дополнительный участник, добавим заголовок
            if (cast.others.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Others")
                this += cast.others.map { MovieCastRVItem.PersonItem(it) }
            }
        }


        return MovieCastState.Content(
            fullTitle = cast.fullTitle,
            items = items
        )
    }
}