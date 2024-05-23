package com.practicum.movieexample.ui.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.movieexample.R
import com.practicum.movieexample.databinding.ActivityMainBinding
import com.practicum.movieexample.navigation.NavigatorHolder
import com.practicum.movieexample.navigation.NavigatorImpl
import org.koin.android.ext.android.inject

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator = NavigatorImpl(R.id.mainContainer, supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigator.openFragment(MoviesFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.attachNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.detachNavigator()
    }

}