package com.practicum.movieexample.domain.models.search

data class Movie(
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String,
    val inFavourite: Boolean
)