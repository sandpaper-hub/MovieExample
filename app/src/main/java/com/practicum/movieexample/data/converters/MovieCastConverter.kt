package com.practicum.movieexample.data.converters

import android.util.Log
import com.practicum.movieexample.data.dto.cast.Actor
import com.practicum.movieexample.data.dto.cast.Directors
import com.practicum.movieexample.data.dto.cast.Item
import com.practicum.movieexample.data.dto.cast.MovieCastsResponse
import com.practicum.movieexample.data.dto.cast.Other
import com.practicum.movieexample.data.dto.cast.Writers
import com.practicum.movieexample.domain.models.cast.MovieCastPerson
import com.practicum.movieexample.domain.models.cast.MovieCasts

class MovieCastConverter {
    fun convert(response: MovieCastsResponse): MovieCasts {
        return with(response) {
            MovieCasts(
                response.imDbId,
                response.fullTitle,
                convertDirectors(response.directors),
                convertOthers(response.others),
                convertWriters(response.writers),
                convertActors(response.actors)
            )
        }
    }

    private fun convertDirectors(directorResponse: Directors): List<MovieCastPerson> {
        return directorResponse.items.map { it.toMovieCastPerson() }
    }

    private fun convertOthers(otherResponses: List<Other>): List<MovieCastPerson> {
        return otherResponses.flatMap { otherResponse ->
            otherResponse.items.map { it.toMovieCastPerson(jobPrefix = otherResponse.job) }
        }
    }

    private fun convertWriters(writerResponse: Writers): List<MovieCastPerson> {
        return writerResponse.items.map {
            it.toMovieCastPerson()
        }
    }

    private fun convertActors(actorsResponses: List<Actor>): List<MovieCastPerson> {
        return actorsResponses.map { actor ->
            MovieCastPerson(actor.id, actor.name, actor.asCharacter, actor.image)
        }
    }

    private fun Item.toMovieCastPerson(jobPrefix: String = ""): MovieCastPerson {
        return MovieCastPerson(
            this.id,
            this.name,
            if (jobPrefix.isEmpty()) this.description else "$jobPrefix -- ${this.description}\n",
            null
        )
    }
}