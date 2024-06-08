package com.practicum.movieexample.domain.api.people

import com.practicum.movieexample.domain.models.people.People
import kotlinx.coroutines.flow.Flow

interface PeopleInteractor {
    fun searchPeople(expression: String): Flow<Pair<List<People>?, String?>>
}