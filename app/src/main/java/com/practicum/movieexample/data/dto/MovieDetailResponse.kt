package com.practicum.movieexample.data.dto

class MovieDetailResponse(
    val id: String,
    val title: String,
    val imDbRating: String,
    val year: String,
    val countries: String,
    val genres: String,
    val directors: String,
    val writers: String,
    val stars: String,
    val plot: String
):Response(){
}