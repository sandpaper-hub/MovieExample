package com.practicum.movieexample.ui.casts

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movieexample.R
import com.practicum.movieexample.presentation.movieCast.MovieCastRVItem

class MovieCastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var persons = emptyList<MovieCastRVItem>()

    override fun getItemViewType(position: Int): Int {
        return when (persons[position]) {
            is MovieCastRVItem.PersonItem -> R.layout.list_item_cast
            is MovieCastRVItem.HeaderItem -> R.layout.list_item_header
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.list_item_cast -> MovieCastViewHolder(parent)
            R.layout.list_item_header -> MovieCastHeaderViewHolder(parent)
            else -> error("Unknown view type create [$viewType]")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.list_item_header -> {
                val headerViewHolder = holder as MovieCastHeaderViewHolder
                headerViewHolder.bind(persons[position] as MovieCastRVItem.HeaderItem)
            }

            R.layout.list_item_cast -> {
                val headerHolder = holder as MovieCastViewHolder
                headerHolder.bind(persons[position] as MovieCastRVItem.PersonItem)
            }

            else -> error("Unknown view type bind [${holder.itemViewType}]")
        }
    }

    override fun getItemCount(): Int = persons.size
}