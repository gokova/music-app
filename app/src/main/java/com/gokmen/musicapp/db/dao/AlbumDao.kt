package com.gokmen.musicapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gokmen.musicapp.db.entity.AlbumEntity
import com.gokmen.musicapp.db.entity.AlbumEntity.Companion.ALBUM_TABLE_NAME
import com.gokmen.musicapp.db.entity.AlbumWithTracks

@Dao
internal interface AlbumDao {
    @Transaction
    @Query("SELECT * FROM $ALBUM_TABLE_NAME")
    fun getAll(): LiveData<List<AlbumWithTracks>>

    @Transaction
    @Query("SELECT * FROM $ALBUM_TABLE_NAME WHERE name=:name AND artist=:artist LIMIT 1")
    fun getByNameAndArtist(name: String, artist: String): AlbumWithTracks?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: AlbumEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(album: AlbumEntity)

    @Delete
    fun delete(album: AlbumEntity)
}
