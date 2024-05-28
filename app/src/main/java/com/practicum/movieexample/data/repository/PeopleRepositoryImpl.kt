package com.practicum.movieexample.data.repository

import com.practicum.movieexample.data.dto.people.PeopleSearchRequest
import com.practicum.movieexample.data.dto.people.PeopleSearchResponse
import com.practicum.movieexample.data.network.NetworkClient
import com.practicum.movieexample.domain.api.people.PeopleRepository
import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.util.Resource

class PeopleRepositoryImpl(private val networkClient: NetworkClient): PeopleRepository {
    override fun searchPeople(expression: String): Resource<List<People>> {
        val response = networkClient.doRequest(PeopleSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                Resource.Success((response as PeopleSearchResponse).results.map {
                    People(
                        it.id,
                        it.image,
                        it.title,
                        it.description
                    )
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}