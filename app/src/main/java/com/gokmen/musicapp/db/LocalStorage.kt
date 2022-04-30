package com.gokmen.musicapp.db

import androidx.lifecycle.LiveData
import com.gokmen.musicapp.db.entity.AlbumWithTracks

interface LocalStorage {

    fun getAlbums(): LiveData<List<AlbumWithTracks>>

    fun getAlbum(name: String, artist: String): LiveData<AlbumWithTracks?>

    fun insertAlbum(album: AlbumWithTracks)

    fun updateAlbum(album: AlbumWithTracks)

    fun deleteAlbum(album: AlbumWithTracks)
}
