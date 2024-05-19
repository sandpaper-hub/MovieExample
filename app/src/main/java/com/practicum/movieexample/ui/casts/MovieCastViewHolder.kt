package com.practicum.movieexample.ui.casts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.movieexample.R
import com.practicum.movieexample.domain.models.MovieCastPerson
import com.practicum.movieexample.presentation.movieCast.MovieCastRVItem

class MovieCastViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.list_item_cast, parent, false
    )
) {
    var actorImage: ImageView = itemView.findViewById(R.id.actorImageView)
    var personName: TextView = itemView.findViewById(R.id.actorNameTextView)
    var personDescription: TextView = itemView.findViewById(R.id.actorDescriptionTextView)

    fun bind(item: MovieCastRVItem.PersonItem) {
        if (item.movieCastPerson.image == null) {
            actorImage.visibility = View.GONE
        } else {
            Glide.with(itemView).load(item.movieCastPerson.image).into(actorImage)
            actorImage.visibility = View.VISIBLE
        }
        personName.text = item.movieCastPerson.name
        personDescription.text = "${item.movieCastPerson.description}"
    }
}