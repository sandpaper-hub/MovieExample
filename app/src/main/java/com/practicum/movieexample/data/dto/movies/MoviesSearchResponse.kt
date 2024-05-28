package com.practicum.movieexample.data.dto.movies

import com.practicum.movieexample.data.dto.Response

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>
) : Response()