package com.practicum.movieexample.data.network

import com.practicum.movieexample.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}