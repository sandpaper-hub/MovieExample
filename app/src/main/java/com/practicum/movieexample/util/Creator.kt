package com.practicum.movieexample.util

import android.app.Application

object Creator {
    private lateinit var application: Application
    fun initializeCreatorValues(application: Application) {
        this.application = application
    }
}