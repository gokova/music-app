package com.gokmen.musicapp.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumWithTracks(
    @Embedded
    val album: AlbumEntity,
    @Relation(
        parentColumn = AlbumEntity.ALBUM_ID,
        entityColumn = TrackEntity.TRACK_ALBUM_ID
    )
    var tracks: List<TrackEntity> = listOf()
)
