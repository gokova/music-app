package com.gokmen.musicapp.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = TrackEntity.TRACK_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = arrayOf(AlbumEntity.ALBUM_ID),
            childColumns = arrayOf(TrackEntity.TRACK_ALBUM_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val albumId: String,
    val name: String
) {

    companion object {
        internal const val TRACK_TABLE_NAME = "tracks"
        internal const val TRACK_ALBUM_ID = "albumId"
    }
}
