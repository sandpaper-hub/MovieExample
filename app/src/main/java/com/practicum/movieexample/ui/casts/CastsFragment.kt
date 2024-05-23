package com.practicum.movieexample.ui.casts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.practicum.movieexample.R
import com.practicum.movieexample.databinding.FragmentCastsBinding
import com.practicum.movieexample.presentation.movieCast.MovieCastViewModel
import com.practicum.movieexample.ui.FragmentExample
import com.practicum.movieexample.ui.casts.model.MovieCastState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CastsFragment : Fragment() {
    companion object {
        private const val MOVIE_ID = "MOVIE"
        const val TAG = "CastsFragment"
    }

    private lateinit var binding: FragmentCastsBinding
    private val viewModel: MovieCastViewModel by viewModel {
        parametersOf(requireArguments().getString("MOVIE_ID"))
    }

    private val adapter =
        ListDelegationAdapter(movieCastHeaderDelegate(), movieCastPersonDelegate())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCastsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.movieTitle.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainContainer, FragmentExample())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit()
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: MovieCastState) {
        when (state) {
            is MovieCastState.Loading -> showLoading()
            is MovieCastState.Content -> showContent(state)
            is MovieCastState.Error -> showError(state)
        }
    }

    private fun showError(state: MovieCastState.Error) = with(binding) {
        progressBar.visibility = View.GONE
        contentContainer.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        placeholderMessage.text = state.message
    }

    private fun showContent(state: MovieCastState.Content) = with(binding) {
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE

        movieTitle.text = state.fullTitle
        adapter.items = state.items
        adapter.notifyDataSetChanged()
    }

    private fun showLoading() = with(binding) {
        contentContainer.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
}