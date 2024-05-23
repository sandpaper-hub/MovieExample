package com.practicum.movieexample.di

import com.practicum.movieexample.navigation.NavigatorHolder
import com.practicum.movieexample.navigation.Router
import com.practicum.movieexample.navigation.RouterImpl
import org.koin.dsl.module

val navigationModule= module {
    val router = RouterImpl()

    single<Router> { router }
    single<NavigatorHolder> { router.navigatorHolder }
}