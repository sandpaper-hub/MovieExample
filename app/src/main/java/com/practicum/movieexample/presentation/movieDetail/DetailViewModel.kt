package com.practicum.movieexample.presentation.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.movieexample.data.dto.detail.MovieDetailResponse
import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.api.movies.MoviesInteractor
import com.practicum.movieexample.ui.movieDetail.model.DetailState

class DetailViewModel(
    val id: String,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {

    init {
        moviesInteractor.searchMovie(id, object : MoviesInteractor.MovieDetailConsumer {
            override fun consume(foundMovie: Response?, errorMessage: String?) {
                when {
                    errorMessage != null -> {
                        renderState(DetailState.Error(errorMessage))
                    }

                    foundMovie != null -> {
                        renderState(DetailState.Content(foundMovie as MovieDetailResponse))
                    }
                }
            }

        })
    }

    private val idLiveData = MutableLiveData<DetailState>()

    fun observeId(): LiveData<DetailState> = idLiveData

    private fun renderState(state: DetailState) {
        idLiveData.postValue(state)
    }
}