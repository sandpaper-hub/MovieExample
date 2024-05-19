package com.practicum.movieexample.di

import com.practicum.movieexample.data.MoviesRepositoryImpl
import com.practicum.movieexample.data.converters.MovieCastConverter
import com.practicum.movieexample.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { MovieCastConverter() }
    factory<MoviesRepository> {
        MoviesRepositoryImpl(get(), get(), get())
    }
}