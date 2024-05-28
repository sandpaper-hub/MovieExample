package com.practicum.movieexample.domain.api.people

import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.util.Resource

interface PeopleRepository {
    fun searchPeople(expression: String): Resource<List<People>>
}