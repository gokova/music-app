package com.gokmen.musicapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val albumId: String,
    val name: String
) : Parcelable
