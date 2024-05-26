package com.practicum.movieexample.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.practicum.movieexample.R
import com.practicum.movieexample.data.dto.MovieDetailResponse
import com.practicum.movieexample.databinding.FragmentDetailMovieBinding
import com.practicum.movieexample.presentation.movieDetail.DetailViewModel
import com.practicum.movieexample.ui.casts.CastsFragment
import com.practicum.movieexample.ui.movieDetail.model.DetailState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMovieFragment : Fragment() {
    companion object {
        private const val ID = "id"

        fun newInstance(id: String) = DetailMovieFragment().apply {
            arguments = Bundle().apply {
                putString(ID, id)
            }
        }
    }

    private lateinit var binding: FragmentDetailMovieBinding
    private val detailViewModel: DetailViewModel by viewModel<DetailViewModel> {
        parametersOf(requireArguments().getString(ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.observeId().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.castButton.setOnClickListener {
            findNavController().navigate(R.id.action_aboutMovieFragment_to_castsFragment, CastsFragment.createArguments(detailViewModel.id))
        }
    }

    private fun render(state: DetailState) {
        when (state) {
            is DetailState.Error -> showError(state.errorMessage)
            is DetailState.Content -> showContent(state.foundMovie)
        }
    }

    private fun showContent(foundMovie: MovieDetailResponse) = with(binding) {
        movieName.text = foundMovie.title
        ratingDescription.text = foundMovie.imDbRating
        countryDescription.text = foundMovie.countries
        genreDescription.text = foundMovie.genres
        directorDescription.text = foundMovie.directors
        scenaristDescription.text = foundMovie.writers
        rolesDescription.text = foundMovie.stars
        movieDescription.text = foundMovie.plot
    }

    private fun showError(errorMessage: String) = with(binding) {
        detailGroup.visibility = View.GONE
        errorMessageTextView.visibility = View.VISIBLE
        errorMessageTextView.text = errorMessage
    }
}