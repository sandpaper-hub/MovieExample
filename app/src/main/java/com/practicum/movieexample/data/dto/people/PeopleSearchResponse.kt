package com.practicum.movieexample.data.dto.people

import com.practicum.movieexample.data.dto.Response

class PeopleSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<PeopleDto>
): Response()