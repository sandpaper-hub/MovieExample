package com.practicum.movieexample.presentation.people

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.movieexample.domain.api.people.PeopleInteractor
import android.os.Handler
import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.ui.people.model.PeopleState

class PeopleSearchViewModel(private val peopleInteractor: PeopleInteractor) : ViewModel() {
    companion object{
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<PeopleState>()
    private val mediatorStateLiveData = MediatorLiveData<PeopleState>().also { liveData ->
        liveData.addSource(stateLiveData) {peopleState ->
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

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }
        lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(PeopleState.Loading)

            peopleInteractor.searchPeople(newSearchText, object : PeopleInteractor.PeopleConsumer{
                override fun consume(foundPeople: List<People>?, erroeMessage: String?) {
                    val people = mutableListOf<People>()
                    if (foundPeople != null) {
                        people.addAll(foundPeople)
                    }

                    when {
                        erroeMessage != null -> {
                            renderState(PeopleState.Error("Something went wrong"))
                        }

                        people.isEmpty() -> renderState(PeopleState.Empty("Nothing found"))
                        else -> renderState(PeopleState.Content(people))
                    }
                }

            })
        }
    }

    private fun renderState(state: PeopleState) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(searchRunnable)
    }
}