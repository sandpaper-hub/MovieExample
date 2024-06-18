package com.practicum.movieexample.presentation.movies

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.movieexample.domain.api.movies.MoviesInteractor
import com.practicum.movieexample.domain.models.search.Movie
import com.practicum.movieexample.ui.movies.model.MoviesState
import com.practicum.movieexample.ui.movies.model.SingleLiveEvent
import com.practicum.movieexample.util.debounce
import kotlinx.coroutines.launch

class MoviesSearchViewModel(private val moviesInteractor: MoviesInteractor) : ViewModel() {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val stateLiveData = MutableLiveData<MoviesState>()

    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                is MoviesState.Content -> MoviesState.Content(movieState.movies.sortedByDescending { it.inFavourite })
                is MoviesState.Empty -> movieState
                is MoviesState.Error -> movieState
                is MoviesState.Loading -> movieState
            }
        }
    }

    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeShowToast(): LiveData<String?> = showToast

    private var lastSearchText: String? = null

    private val movieSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, false) { changedText ->
            searchRequest(changedText)
        }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }
        lastSearchText = changedText
        movieSearchDebounce(changedText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            renderState(MoviesState.Loading)

            viewModelScope.launch {
                moviesInteractor.searchMovies(newSearchText).collect { pair ->
                    val movies = mutableListOf<Movie>()
                    if (pair.first != null) {
                        movies.addAll(pair.first!!)
                    }

                    when {
                        pair.second != null -> renderState(MoviesState.Error("Something went wrong"))
                        movies.isEmpty() -> renderState(MoviesState.Empty("Nothing found"))
                        else ->renderState(MoviesState.Content(movies))
                    }
                }
            }
        }
    }

    fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    fun toggleFavorite(movie: Movie) {
        if (movie.inFavourite) {
            moviesInteractor.removeMovieFromFavorites(movie)
        } else {
            moviesInteractor.addMovieToFavorites(movie)
        }

        updateMovieContent(movie.id, movie.copy(inFavourite = !movie.inFavourite))
    }

    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value

        if (currentState is MoviesState.Content) {
            val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }

            if (movieIndex != -1) {
                stateLiveData.value = MoviesState.Content(currentState.movies.toMutableList().also {
                    it[movieIndex] = newMovie
                })
            }
        }
    }
}