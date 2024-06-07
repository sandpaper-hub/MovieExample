package com.practicum.movieexample.ui.movies

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
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
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setRestoreState(false)
            val options = builder.build()

            when (item.itemId) {
                R.id.moviesFragment -> {
                    navController.navigate(R.id.moviesFragment, null, options)
                    true
                }

                R.id.peopleFragment -> {
                    navController.navigate(R.id.peopleFragment, null, options)
                    true
                }

                R.id.aboutFragment -> {
                    navController.navigate(R.id.aboutFragment, null, options)
                    true
                }

                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
