package com.practicum.movieexample.ui.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.movieexample.R
import com.practicum.movieexample.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(MoviesFragment(), null).commit()
    }
}