package com.practicum.movieexample.data.converters

import android.util.Log
import com.practicum.movieexample.data.dto.Actor
import com.practicum.movieexample.data.dto.Directors
import com.practicum.movieexample.data.dto.Item
import com.practicum.movieexample.data.dto.MovieCastsResponse
import com.practicum.movieexample.data.dto.Other
import com.practicum.movieexample.data.dto.Writers
import com.practicum.movieexample.domain.models.MovieCastPerson
import com.practicum.movieexample.domain.models.MovieCasts

class MovieCastConverter {
    fun convert(response: MovieCastsResponse): MovieCasts {
        Log.d("SAMPLE123", response.toString())
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