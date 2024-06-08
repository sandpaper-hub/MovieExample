package com.practicum.movieexample.data.repository

import com.practicum.movieexample.data.dto.people.PeopleSearchRequest
import com.practicum.movieexample.data.dto.people.PeopleSearchResponse
import com.practicum.movieexample.data.network.NetworkClient
import com.practicum.movieexample.domain.api.people.PeopleRepository
import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PeopleRepositoryImpl(private val networkClient: NetworkClient) : PeopleRepository {
    override fun searchPeople(expression: String): Flow<Resource<List<People>>> = flow {
        val response = networkClient.doRequest(PeopleSearchRequest(expression))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 -> {
                with(response as PeopleSearchResponse) {
                    val data = results.map { People(it.id, it.image, it.title, it.description) }
                    emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}