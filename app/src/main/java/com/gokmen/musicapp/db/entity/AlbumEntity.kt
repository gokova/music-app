package com.gokmen.musicapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AlbumEntity.ALBUM_TABLE_NAME)
data class AlbumEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val artist: String,
    val thumbnailUrl: String,
    val coverUrl: String
) {

    companion object {
        internal const val ALBUM_TABLE_NAME = "albums"
        internal const val ALBUM_ID = "id"
    }
}
