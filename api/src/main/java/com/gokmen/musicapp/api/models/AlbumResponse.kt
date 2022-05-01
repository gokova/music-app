package com.gokmen.musicapp.api.models

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    @SerializedName("topalbums")
    val topAlbums: AlbumResults?
)

data class AlbumResults(
    @SerializedName("album")
    val albums: List<TopAlbum>?
)

data class TopAlbum(
    @SerializedName("mbid")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("artist")
    val artist: AlbumArtist?,
    @SerializedName("image")
    val images: List<AlbumCover>?
)

data class AlbumArtist(
    @SerializedName("name")
    val name: String?
)

data class AlbumCover(
    @SerializedName("#text")
    val url: String?,
    @SerializedName("size")
    val size: String?
)
