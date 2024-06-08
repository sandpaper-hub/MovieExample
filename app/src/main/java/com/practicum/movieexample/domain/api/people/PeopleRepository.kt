package com.practicum.movieexample.domain.api.people

import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.util.Resource
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    fun searchPeople(expression: String):Flow<Resource<List<People>>>
}