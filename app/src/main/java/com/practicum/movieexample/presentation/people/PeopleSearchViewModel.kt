package com.practicum.movieexample.presentation.people

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.movieexample.domain.api.people.PeopleInteractor
import android.os.Handler
import androidx.lifecycle.viewModelScope
import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.ui.people.model.PeopleState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PeopleSearchViewModel(private val peopleInteractor: PeopleInteractor) : ViewModel() {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private var searchJob: Job? = null

    private val handler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<PeopleState>()
    private val mediatorStateLiveData = MediatorLiveData<PeopleState>().also { liveData ->
        liveData.addSource(stateLiveData) { peopleState ->
            liveData.value = when (peopleState) {
                is PeopleState.Loading -> PeopleState.Loading
                is PeopleState.Content -> PeopleState.Content(peopleState.people)
                is PeopleState.Error -> PeopleState.Error(peopleState.message)
                is PeopleState.Empty -> PeopleState.Empty(peopleState.message)
            }
        }
    }

    fun observeState(): LiveData<PeopleState> = mediatorStateLiveData

    private var lastSearchText: String? = null

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }
        lastSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(PeopleState.Loading)


            viewModelScope.launch {
                peopleInteractor.searchPeople(newSearchText).collect { pair ->
                    processResult(pair.first, pair.second)
                }
            }
        }
    }

    private fun processResult(foundNames: List<People>?, errorMessage: String?) {
        val people = mutableListOf<People>()
        if (foundNames != null) {
            people.addAll(foundNames)
        }

        when {
            errorMessage != null -> {
                renderState(PeopleState.Error("Something went wrong"))
            }

            people.isEmpty() -> renderState(PeopleState.Empty("Nothing found"))
            else -> renderState(PeopleState.Content(people))
        }
    }

    private fun renderState(state: PeopleState) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
    }
}