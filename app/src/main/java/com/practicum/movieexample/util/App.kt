package com.practicum.movieexample.util

import android.app.Application
import com.practicum.movieexample.di.dataModule
import com.practicum.movieexample.di.interactorModule
import com.practicum.movieexample.di.repositoryModule
import com.practicum.movieexample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin{
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}