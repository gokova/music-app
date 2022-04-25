package com.gokmen.musicapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gokmen.musicapp.databinding.ArtistListItemBinding
import com.gokmen.musicapp.models.Artist

class ArtistAdapter(
    private val onArtistSelect: ((Artist) -> Unit)?
) : ListAdapter<Artist, ArtistAdapter.ArtistViewHolder>(ArtistItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArtistListItemBinding.inflate(inflater, parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(getItem(position), onArtistSelect)
    }

    class ArtistViewHolder(
        private val binding: ArtistListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Artist, onArtistSelect: ((Artist) -> Unit)?) {
            binding.tvArtistName.text = item.name
            binding.root.setOnClickListener {
                onArtistSelect?.invoke(item)
            }
        }
    }
}
