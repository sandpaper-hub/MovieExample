package com.practicum.movieexample.data.dto

class MovieCastsResponse( val actors: List<Actor>,
                          val directors: Directors,
                          val errorMessage: String,
                          val fullTitle: String,
                          val imDbId: String,
                          val others: List<Other>,
                          val title: String,
                          val type: String,
                          val writers: Writers,
                          val year: String  ): Response() {
}