package com.gokmen.musicapp.utils

import com.gokmen.musicapp.api.AlbumTrack
import com.gokmen.musicapp.api.SearchArtist
import com.gokmen.musicapp.api.TopAlbum
import com.gokmen.musicapp.db.entity.AlbumEntity
import com.gokmen.musicapp.db.entity.AlbumWithTracks
import com.gokmen.musicapp.db.entity.TrackEntity
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.models.Artist
import com.gokmen.musicapp.models.Track
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

internal fun AlbumTrack.toTrack(albumId: String): Track {
    val name = name ?: ""

    return Track(
        albumId = albumId,
        name = name
    )
}

internal fun AlbumWithTracks.toAlbum(): Album {
    val tracks = this.tracks.map { it.toTrack() }

    return Album(
        id = album.id,
        name = album.name,
        artist = album.artist,
        thumbnailUrl = album.thumbnailUrl,
        coverUrl = album.coverUrl,
        isSaved = true,
        tracks = tracks
    )
}

internal fun TrackEntity.toTrack(): Track {
    return Track(
        albumId = albumId,
        name = name
    )
}

internal fun Album.toDbModel(): AlbumWithTracks {
    val album = AlbumEntity(
        id = id,
        name = name,
        artist = artist,
        thumbnailUrl = thumbnailUrl,
        coverUrl = coverUrl
    )
    val tracks = this.tracks?.map { it.toDbModel() } ?: listOf()
    return AlbumWithTracks(album, tracks)
}

internal fun Track.toDbModel(): TrackEntity {
    return TrackEntity(
        albumId = albumId,
        name = name
    )
}
