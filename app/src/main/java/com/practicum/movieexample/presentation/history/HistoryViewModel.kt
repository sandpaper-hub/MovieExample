package com.practicum.movieexample.presentation.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.movieexample.domain.db.HistoryInteractor
import com.practicum.movieexample.domain.models.search.Movie
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyInteractor: HistoryInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<HistoryState>()
    fun observeState(): LiveData<HistoryState> = stateLiveData

    fun fillData() {
        renderState(HistoryState.Loading)
        viewModelScope.launch {
            historyInteractor.historyMovies().collect{
                movies -> processResult(movies)
            }
        }
    }

    private fun processResult(movies: List<Movie>) {
        if (movies.isEmpty()) {
            renderState(HistoryState.Empty("Nothing searched yet"))
        } else {
            renderState(HistoryState.Content(movies))
        }
    }

    private fun renderState(state: HistoryState) {
        stateLiveData.postValue(state)
    }
}