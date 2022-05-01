package com.gokmen.musicapp.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gokmen.musicapp.databinding.TrackListItemBinding
import com.gokmen.musicapp.models.Track

class TrackAdapter : ListAdapter<Track, TrackAdapter.TrackViewHolder>(TrackItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TrackListItemBinding.inflate(inflater, parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrackViewHolder(
        private val binding: TrackListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Track) {
            binding.tvTrackName.text = item.name
        }
    }
}
