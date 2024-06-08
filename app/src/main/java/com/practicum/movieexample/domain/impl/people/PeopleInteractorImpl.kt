package com.practicum.movieexample.domain.impl.people

import com.practicum.movieexample.domain.api.people.PeopleInteractor
import com.practicum.movieexample.domain.api.people.PeopleRepository
import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class PeopleInteractorImpl(private val peopleRepository: PeopleRepository) : PeopleInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchPeople(expression: String): Flow<Pair<List<People>?, String?>> {
        return peopleRepository.searchPeople(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

}