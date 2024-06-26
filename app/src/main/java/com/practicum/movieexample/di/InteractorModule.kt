package com.practicum.movieexample.di

import com.practicum.movieexample.domain.api.movies.MoviesInteractor
import com.practicum.movieexample.domain.api.people.PeopleInteractor
import com.practicum.movieexample.domain.db.HistoryInteractor
import com.practicum.movieexample.domain.impl.history.HistoryInteractorImpl
import com.practicum.movieexample.domain.impl.movies.MoviesInteractorImpl
import com.practicum.movieexample.domain.impl.people.PeopleInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

    single<PeopleInteractor> {
        PeopleInteractorImpl(get())
    }

    single <HistoryInteractor>{
        HistoryInteractorImpl(get())
    }
}