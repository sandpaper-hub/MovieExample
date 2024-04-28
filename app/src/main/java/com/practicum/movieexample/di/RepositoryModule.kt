package com.practicum.movieexample.di

import com.practicum.movieexample.data.MoviesRepositoryImpl
import com.practicum.movieexample.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }
}