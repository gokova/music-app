package com.gokmen.musicapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val id: String,
    val name: String,
    val artist: String,
    val thumbnailUrl: String,
    val coverUrl: String,
    val isSaved: Boolean = false,
    var tracks: List<Track>? = null
) : Parcelable
