package com.practicum.movieexample.ui.casts

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.movieexample.databinding.ActivityCastsBinding
import com.practicum.movieexample.presentation.movieCast.MovieCastViewModel
import com.practicum.movieexample.ui.casts.model.MovieCastState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CastsActivity : AppCompatActivity() {
    companion object {
        private const val MOVIE_ID = "MOVIE"
    }

    private lateinit var binding: ActivityCastsBinding
    private val viewModel: MovieCastViewModel by viewModel {
        parametersOf(intent.getStringExtra(MOVIE_ID))
    }

    private val adapter = MovieCastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCastsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.observeState().observe(this) {
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
        errorMessage.visibility = View.GONE
        errorMessage.text = state.message
    }

    private fun showContent(state: MovieCastState.Content) = with(binding) {
        progressBar.visibility = View.GONE
        errorMessage.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE

        movieTitle.text = state.fullTitle
        adapter.persons = state.items
        adapter.notifyDataSetChanged()
    }

    private fun showLoading() = with(binding) {
        contentContainer.visibility = View.GONE
        errorMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }


}