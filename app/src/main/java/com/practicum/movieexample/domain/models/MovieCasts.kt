package com.practicum.movieexample.domain.models

data class MovieCasts(
    val imdbId: String,
    val fullTitle: String,
    val directors: List<MovieCastPerson>,
    val writers: List<MovieCastPerson>,
    val actors: List<MovieCastPerson>,
    val others: List<MovieCastPerson>,
)