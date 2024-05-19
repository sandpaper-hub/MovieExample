package com.practicum.movieexample.presentation.movieCast

import com.practicum.movieexample.domain.models.MovieCastPerson

sealed interface MovieCastRVItem {
    data class HeaderItem(val headerText: String) : MovieCastRVItem
    data class PersonItem(val movieCastPerson: MovieCastPerson): MovieCastRVItem
}