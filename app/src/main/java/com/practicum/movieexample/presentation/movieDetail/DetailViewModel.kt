package com.practicum.movieexample.presentation.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.movieexample.data.dto.detail.MovieDetailResponse
import com.practicum.movieexample.data.dto.Response
import com.practicum.movieexample.domain.api.movies.MoviesInteractor
import com.practicum.movieexample.ui.movieDetail.model.DetailState
import kotlinx.coroutines.launch

class DetailViewModel(
    val id: String,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {

    init {
        viewModelScope.launch {
            moviesInteractor.searchMovie(id).collect{ pair ->
                when{
                    pair.second!= null -> {
                        renderState(DetailState.Error(pair.second.toString()))
                    }

                    pair.first != null -> {
                        renderState(DetailState.Content(pair.first as MovieDetailResponse))
                    }
                }
            }
        }
    }

    private val idLiveData = MutableLiveData<DetailState>()

    fun observeId(): LiveData<DetailState> = idLiveData

    private fun renderState(state: DetailState) {
        idLiveData.postValue(state)
    }
}