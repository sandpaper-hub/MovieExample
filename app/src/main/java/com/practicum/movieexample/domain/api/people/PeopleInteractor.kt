package com.practicum.movieexample.domain.api.people

import com.practicum.movieexample.domain.models.people.People

interface PeopleInteractor {
    fun searchPeople(expression: String, consumer: PeopleConsumer)

    interface PeopleConsumer{
        fun consume(foundPeople: List<People>?, erroeMessage: String?)
    }
}