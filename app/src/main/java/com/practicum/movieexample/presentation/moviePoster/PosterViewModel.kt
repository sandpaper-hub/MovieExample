package com.practicum.movieexample.presentation.moviePoster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PosterViewModel(private val posterUrl: String) : ViewModel() {

    private val liveData = MutableLiveData<String>(posterUrl)
    fun observeUrl() = liveData
}