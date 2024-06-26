package com.practicum.movieexample.ui.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movieexample.domain.models.search.Movie

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {
    var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder = HistoryViewHolder(parent)

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

}