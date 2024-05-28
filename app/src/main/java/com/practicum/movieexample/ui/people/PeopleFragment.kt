package com.practicum.movieexample.ui.people

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.movieexample.databinding.FragmentPeopleBinding
import com.practicum.movieexample.domain.models.people.People
import com.practicum.movieexample.presentation.people.PeopleSearchViewModel
import com.practicum.movieexample.ui.people.model.PeopleState
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    private lateinit var binding: FragmentPeopleBinding
    private val viewModel by viewModel<PeopleSearchViewModel>()

    companion object{
        private const val CLICK_DEBOUNCE = 1000L
    }

    private var isClickAllowed = true
    private val adapter = PeopleAdapter()
    private var textWatcher: TextWatcher? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }


        textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        textWatcher.let { binding.searchPeopleEditText.addTextChangedListener(it) }

        binding.peopleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.peopleRecyclerView.adapter = adapter
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE)
        }
        return current
    }

    private fun showLoading() = with(binding) {
        progressBar.visibility = View.VISIBLE

        peopleRecyclerView.visibility = View.GONE
        searchPeopleEditText.visibility = View.GONE
    }

    private fun showError(errorMessage: String) = with(binding) {
        placeholderMessage.visibility = View.VISIBLE
        placeholderMessage.text = errorMessage

        peopleRecyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        searchPeopleEditText.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(people: List<People>) = with(binding) {
        peopleRecyclerView.visibility = View.VISIBLE
        searchPeopleEditText.visibility = View.VISIBLE
        adapter.people.clear()
        adapter.people.addAll(people)
        adapter.notifyDataSetChanged()

        progressBar.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
    }

    private fun render(state: PeopleState) {
        when (state) {
            is PeopleState.Error -> showError(state.message)
            is PeopleState.Content -> showContent(state.people)
            is PeopleState.Loading -> showLoading()
            is PeopleState.Empty -> showError(state.message)
        }
    }
}