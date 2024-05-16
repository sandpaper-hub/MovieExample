package com.practicum.movieexample.ui.movieDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.movieexample.databinding.ActivityPosterBinding

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPosterBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPosterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.extras?.getString("id")
        val url = intent.extras?.getString("poster")
        val adapter = DetailMovieViewPagerAdapter(id!!,url!!, supportFragmentManager, lifecycle)
        binding.detailMovieViewPager.adapter = adapter
        tabLayoutMediator = TabLayoutMediator(
            binding.detailMovieTabLayout,
            binding.detailMovieViewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Poster"
                1 -> tab.text = "Detail"
            }
        }

        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}