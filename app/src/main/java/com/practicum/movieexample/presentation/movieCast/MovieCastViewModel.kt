package com.practicum.movieexample.presentation.movieCast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.movieexample.domain.api.movies.MoviesInteractor
import com.practicum.movieexample.domain.models.cast.MovieCasts
import com.practicum.movieexample.ui.casts.model.MovieCastState
import kotlinx.coroutines.launch

class MovieCastViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {
    private val liveData = MutableLiveData<MovieCastState>()
    fun observeState() = liveData

    init {
        liveData.postValue(MovieCastState.Loading)
        viewModelScope.launch {
            moviesInteractor.getMovieCasts(movieId).collect { pair ->
                when {
                    pair.first != null -> {
                        liveData.postValue(castToUiStateContent(pair.first as MovieCasts))
                    }

                    else -> {
                        liveData.postValue(MovieCastState.Error(pair.second))
                    }
                }
            }
        }
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