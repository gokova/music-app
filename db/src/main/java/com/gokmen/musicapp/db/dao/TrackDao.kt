package com.gokmen.musicapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gokmen.musicapp.db.entity.TrackEntity
import com.gokmen.musicapp.db.entity.TrackEntity.Companion.TRACK_TABLE_NAME

@Dao
internal interface TrackDao {
    @Query("SELECT * FROM $TRACK_TABLE_NAME")
    fun getAll(): LiveData<List<TrackEntity>>

    @Query("SELECT * FROM $TRACK_TABLE_NAME WHERE albumId=:albumId LIMIT 1")
    fun findByAlbumId(albumId: String): LiveData<List<TrackEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: TrackEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(track: TrackEntity)

    @Delete
    fun delete(track: TrackEntity)

    @Query("DELETE FROM $TRACK_TABLE_NAME WHERE albumId=:albumId")
    fun deleteByAlbumId(albumId: String)
}
