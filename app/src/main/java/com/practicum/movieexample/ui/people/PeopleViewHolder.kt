package com.practicum.movieexample.ui.people

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.movieexample.R
import com.practicum.movieexample.domain.models.people.People

class PeopleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.people_view, parent, false)
) {
    private val peopleImage = itemView.findViewById<ImageView>(R.id.peopleImage)
    private val peopleName = itemView.findViewById<TextView>(R.id.peopleNameText)
    private val peopleDescription = itemView.findViewById<TextView>(R.id.peopleDescriptionText)

    fun bind(people: People) {
        Glide.with(peopleImage).load(people.image).circleCrop().into(peopleImage)
        peopleName.text = people.title
        peopleDescription.text = people.description
    }
}