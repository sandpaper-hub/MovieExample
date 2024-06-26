package com.practicum.movieexample.data.converters

import com.practicum.movieexample.data.db.entity.MovieEntity
import com.practicum.movieexample.data.dto.movies.MovieDto
import com.practicum.movieexample.domain.models.search.Movie

class MovieDbConverter {
    fun map(movie: MovieDto): MovieEntity {
        return MovieEntity(movie.id, movie.resultType, movie.image, movie.title, movie.description)
    }

    fun map(movie: MovieEntity): Movie {
        return Movie(movie.id, movie.resultType, movie.image, movie.title, movie.description, false)
    }
}