package com.practicum.movieexample.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.movieexample.databinding.FragmentAboutMovieBinding

class AboutMovieFragment : Fragment() {
    private lateinit var binding: FragmentAboutMovieBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aboutMovieData = arguments?.getStringArrayList("POSTER")

        val adapter = DetailMovieViewPagerAdapter(aboutMovieData?.get(0).toString(),
            aboutMovieData?.get(1).toString(), childFragmentManager, lifecycle)

        binding.detailMovieViewPager.adapter = adapter
        tabLayoutMediator = TabLayoutMediator(
            binding.detailMovieTabLayout,
            binding.detailMovieViewPager
        ) {tab, position ->
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