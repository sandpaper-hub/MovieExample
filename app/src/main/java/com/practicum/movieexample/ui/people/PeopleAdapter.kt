package com.practicum.movieexample.ui.people

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movieexample.domain.models.people.People

class PeopleAdapter : RecyclerView.Adapter<PeopleViewHolder>() {

    var people = ArrayList<People>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder =
        PeopleViewHolder(parent)

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(people[position])
    }
}