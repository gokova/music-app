package com.gokmen.musicapp.api

import com.google.gson.annotations.SerializedName

data class AlbumInfoResponse(
    @SerializedName("album")
    val album: AlbumInfoResults?
)

data class AlbumInfoResults(
    @SerializedName("tracks")
    val trackResults: TrackResults?
)

data class TrackResults(
    @SerializedName("track")
    val tracks: List<AlbumTrack>?
)

data class AlbumTrack(
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("name")
    val name: String?
)
