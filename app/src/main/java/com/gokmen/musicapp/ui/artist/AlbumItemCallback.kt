package com.gokmen.musicapp.ui.artist

import androidx.recyclerview.widget.DiffUtil
import com.gokmen.musicapp.models.Album

class AlbumItemCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}
