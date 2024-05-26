package com.practicum.movieexample.ui.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.movieexample.R
import com.practicum.movieexample.databinding.FragmentMainBinding
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.presentation.movies.MoviesSearchViewModel
import com.practicum.movieexample.ui.movieDetail.AboutMovieFragment
import com.practicum.movieexample.ui.movies.model.MoviesState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val viewModel by viewModel<MoviesSearchViewModel>()


    private val movieAdapter = MovieAdapter(
        object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                if (clickDebounce()) {
                    val aboutMovieData = mutableListOf(movie.id, movie.image)
                    findNavController().navigate(
                        R.id.action_moviesFragment_to_aboutMovieFragment,
                        AboutMovieFragment.createArrayData(aboutMovieData)
                    )
                }
            }

            override fun onFavouriteToggleClick(movie: Movie) {
                viewModel.toggleFavorite(movie)
            }

        }

    )

    private var textWatcher: TextWatcher? = null

    private var isClickAllowed = true

    private val mainHandler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.observeShowToast().observe(viewLifecycleOwner) {
            showToast(it)

        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}

        }

        textWatcher.let { binding.searchEditText.addTextChangedListener(it) }

        binding.movieList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.movieList.adapter = movieAdapter
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun showLoading() {
        binding.movieList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) = with(binding) {
        movieList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    private fun showEmpty(errorMessage: String) = with(binding) {
        movieList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showContent(movies: List<Movie>) = with(binding) {
        movieList.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE

        movieAdapter.movies.clear()
        movieAdapter.movies.addAll(movies)
        movieAdapter.notifyDataSetChanged()
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Empty -> showEmpty(state.message)
            is MoviesState.Error -> showError(state.errorMessage)
        }
    }

    private fun showToast(additionalMessage: String?) {
        Toast.makeText(requireContext(), additionalMessage, Toast.LENGTH_SHORT).show()
    }
}