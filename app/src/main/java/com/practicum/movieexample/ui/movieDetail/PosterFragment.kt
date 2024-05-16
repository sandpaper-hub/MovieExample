package com.practicum.movieexample.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.practicum.movieexample.databinding.FragmentPosterBinding
import com.practicum.movieexample.presentation.movieDetail.PosterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterFragment : Fragment() {
    companion object {
        private const val ID = "id"

        fun newInstance(id: String) = PosterFragment().apply {
            arguments = Bundle().apply {
                putString(ID, id)
            }
        }
    }

    private lateinit var binding: FragmentPosterBinding
    private val viewModel by viewModel<PosterViewModel>{
        parametersOf(requireArguments().getString(ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeUrl().observe(viewLifecycleOwner){
            render(it)
        }
    }

    private fun render(url: String) {
        context?.let { Glide.with(it).load(url).into(binding.posterImageView) }
    }
}