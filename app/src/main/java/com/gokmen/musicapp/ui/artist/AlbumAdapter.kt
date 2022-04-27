package com.gokmen.musicapp.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gokmen.musicapp.R
import com.gokmen.musicapp.databinding.AlbumListItemBinding
import com.gokmen.musicapp.models.Album

class AlbumAdapter(
    private val onAlbumSelect: ((Album) -> Unit)?
) : ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumListItemBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position), onAlbumSelect)
    }

    class AlbumViewHolder(
        private val binding: AlbumListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album, onAlbumSelect: ((Album) -> Unit)?) {
            Glide.with(binding.root)
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_album_cover_24)
                .into(binding.ivAlbumCover)
            binding.tvAlbumName.text = item.name
            binding.root.setOnClickListener {
                onAlbumSelect?.invoke(item)
            }
        }
    }
}
