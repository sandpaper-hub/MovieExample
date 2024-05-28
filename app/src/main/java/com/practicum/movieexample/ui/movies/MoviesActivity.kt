package com.practicum.movieexample.ui.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.practicum.movieexample.R
import com.practicum.movieexample.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.moviesFragment -> navigateToDestination(R.id.moviesFragment, navController)
                R.id.peopleFragment -> navigateToDestination(R.id.peopleFragment, navController)
                R.id.aboutFragment -> navigateToDestination(R.id.aboutFragment, navController)
            }
            true
        }
    }
    private fun navigateToDestination(destinationId: Int, navController: NavController) {
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .setPopUpTo(navController.graph.startDestinationId, false)
            .build()
        navController.navigate(destinationId, null, navOptions)
    }

}