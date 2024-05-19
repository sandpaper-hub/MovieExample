package com.practicum.movieexample.presentation.movieCast

import com.practicum.movieexample.domain.models.MovieCastPerson
import com.practicum.movieexample.ui.casts.RVItem

sealed interface MovieCastRVItem: RVItem {
    data class HeaderItem(val headerText: String) : MovieCastRVItem
    data class PersonItem(val movieCastPerson: MovieCastPerson): MovieCastRVItem
}