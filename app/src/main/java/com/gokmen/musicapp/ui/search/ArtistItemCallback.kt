package com.gokmen.musicapp.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.gokmen.musicapp.models.Artist

class ArtistItemCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}
