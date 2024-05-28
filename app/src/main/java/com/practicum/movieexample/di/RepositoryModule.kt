package com.practicum.movieexample.di

import com.practicum.movieexample.data.repository.MoviesRepositoryImpl
import com.practicum.movieexample.data.converters.MovieCastConverter
import com.practicum.movieexample.data.repository.PeopleRepositoryImpl
import com.practicum.movieexample.domain.api.movies.MoviesRepository
import com.practicum.movieexample.domain.api.people.PeopleRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { MovieCastConverter() }
    factory<MoviesRepository> {
        MoviesRepositoryImpl(get(), get(), get())
    }

    single<PeopleRepository> {
        PeopleRepositoryImpl(get())
    }
}