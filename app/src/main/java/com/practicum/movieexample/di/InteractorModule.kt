package com.practicum.movieexample.di

import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
}