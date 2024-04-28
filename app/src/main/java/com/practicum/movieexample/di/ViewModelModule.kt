package com.practicum.movieexample.di

import com.practicum.movieexample.presentation.movies.MoviesSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module

@KoinReflectAPI
val viewModelModule = module {
    viewModel<MoviesSearchViewModel> {
        MoviesSearchViewModel(get())
    }
}