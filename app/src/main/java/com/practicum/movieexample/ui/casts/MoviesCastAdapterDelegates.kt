package com.practicum.movieexample.ui.casts

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.practicum.movieexample.databinding.ListItemCastBinding
import com.practicum.movieexample.databinding.ListItemHeaderBinding
import com.practicum.movieexample.presentation.movieCast.MovieCastRVItem

fun movieCastHeaderDelegate() = adapterDelegateViewBinding<MovieCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>(
    { layoutInflater, root -> ListItemHeaderBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.headerTextView.text = item.headerText
    }
}

fun movieCastPersonDelegate() = adapterDelegateViewBinding<MovieCastRVItem.PersonItem, RVItem, ListItemCastBinding>(
    { layoutInflater, root -> ListItemCastBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        if (item.movieCastPerson.image == null) {
            binding.actorImageView.isVisible = false
        } else {
            Glide.with(itemView)
                .load(item.movieCastPerson.image)
                .into(binding.actorImageView)
            binding.actorImageView.isVisible = true
        }

        binding.actorNameTextView.text = item.movieCastPerson.name
        binding.actorDescriptionTextView.text = item.movieCastPerson.description
    }
}