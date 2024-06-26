package com.practicum.movieexample.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movieexample.databinding.FragmentHistoryBinding
import com.practicum.movieexample.domain.models.search.Movie
import com.practicum.movieexample.presentation.history.HistoryState
import com.practicum.movieexample.presentation.history.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModel<HistoryViewModel>()

    private var adapter: HistoryAdapter? = null
    private lateinit var placeholderMessage: TextView
    private lateinit var historyList: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryAdapter()

        placeholderMessage = binding.placeholderMessage
        historyList = binding.historyList
        progressBar = binding.progressBar

        historyList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        historyList.adapter = adapter
        viewModel.fillData()

        viewModel.observeState().observe(viewLifecycleOwner){
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        historyList.adapter = null
    }

    private fun render(state: HistoryState) {
        when (state) {
            is HistoryState.Loading -> showLoading()
            is HistoryState.Empty -> showEmpty(state.message)
            is HistoryState.Content -> showContent(state.movies)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(movies: List<Movie>) {
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        historyList.visibility = View.VISIBLE

        adapter?.movies?.clear()
        adapter?.movies?.addAll(movies)
        adapter?.notifyDataSetChanged()
    }

    private fun showEmpty(message: String) {
        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        historyList.visibility = View.GONE

        placeholderMessage.text = message
    }

    private fun showLoading() {
        historyList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }


}