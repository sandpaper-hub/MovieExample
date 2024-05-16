package com.practicum.movieexample.ui.movieDetail.model

import com.practicum.movieexample.data.dto.MovieDetailResponse

sealed interface DetailState {
    data class Error(val errorMessage: String) : DetailState
    data class Content(val foundMovie: MovieDetailResponse) : DetailState
}