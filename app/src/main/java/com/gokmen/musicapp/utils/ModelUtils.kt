package com.gokmen.musicapp.utils

import com.gokmen.musicapp.api.SearchArtist
import com.gokmen.musicapp.api.TopAlbum
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.models.Artist
import java.util.UUID

private const val SMALL = "small"
private const val MEDIUM = "medium"

internal fun SearchArtist.toArtist(): Artist {
    val id = id ?: UUID.randomUUID().toString()
    val name = name ?: ""

    return Artist(
        id = id,
        name = name
    )
}

internal fun TopAlbum.toAlbum(): Album {
    val id = id ?: UUID.randomUUID().toString()
    val name = name ?: ""
    val artist = artist?.name ?: ""
    val smallImage = images?.firstOrNull { it.size == SMALL }?.url ?: ""
    val mediumImage = images?.firstOrNull { it.size == MEDIUM }?.url ?: ""

    return Album(
        id = id,
        name = name,
        artist = artist,
        thumbnailUrl = smallImage,
        coverUrl = mediumImage
    )
}
