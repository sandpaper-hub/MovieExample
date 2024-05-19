package com.practicum.movieexample.di

import com.practicum.movieexample.presentation.movieDetail.DetailViewModel
import com.practicum.movieexample.presentation.movieCast.MovieCastViewModel
import com.practicum.movieexample.presentation.moviePoster.PosterViewModel
import com.practicum.movieexample.presentation.movies.MoviesSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<MoviesSearchViewModel> {
        MoviesSearchViewModel(get())
    }

    viewModel<DetailViewModel> { (movieId: String) ->
        DetailViewModel(movieId, get())
    }

    viewModel<PosterViewModel> { (url: String) ->
        PosterViewModel(url)
    }

    viewModel<MovieCastViewModel> { (movieId: String) ->
        MovieCastViewModel(movieId, get())
    }
}