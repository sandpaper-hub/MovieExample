package com.practicum.movieexample.ui.people.model

import com.practicum.movieexample.domain.models.people.People

sealed interface PeopleState {
    data class Content(val people: List<People>) : PeopleState
    object Loading: PeopleState
    data class Error(val message: String): PeopleState
    data class Empty(val message: String): PeopleState
}