package com.practicum.movieexample.domain.impl.people

import com.practicum.movieexample.domain.api.people.PeopleInteractor
import com.practicum.movieexample.domain.api.people.PeopleRepository
import com.practicum.movieexample.util.Resource
import java.util.concurrent.Executors

class PeopleInteractorImpl(private val peopleRepository: PeopleRepository) : PeopleInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchPeople(expression: String, consumer: PeopleInteractor.PeopleConsumer) {
        executor.execute {
            when(val resource = peopleRepository.searchPeople(expression)){
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }

}