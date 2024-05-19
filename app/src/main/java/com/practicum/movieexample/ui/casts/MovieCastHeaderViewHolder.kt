package com.practicum.movieexample.ui.casts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movieexample.R
import com.practicum.movieexample.presentation.movieCast.MovieCastRVItem

class MovieCastHeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.list_item_header, parent, false
    )
) {
    var headerTextView = itemView.findViewById<TextView>(R.id.headerTextView)

    fun bind(item: MovieCastRVItem.HeaderItem) {
        headerTextView.text = item.headerText
    }
}