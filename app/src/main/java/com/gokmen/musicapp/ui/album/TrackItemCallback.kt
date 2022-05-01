package com.gokmen.musicapp.ui.album

import androidx.recyclerview.widget.DiffUtil
import com.gokmen.musicapp.models.Track

class TrackItemCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}
