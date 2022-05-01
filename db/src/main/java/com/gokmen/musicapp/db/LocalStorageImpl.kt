package com.gokmen.musicapp.db

import androidx.lifecycle.LiveData
import com.gokmen.musicapp.db.dao.AlbumDao
import com.gokmen.musicapp.db.dao.TrackDao
import com.gokmen.musicapp.db.entity.AlbumWithTracks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LocalStorageImpl @Inject constructor(
    private val albumDao: AlbumDao,
    private val trackDao: TrackDao
) : LocalStorage {

    override fun getAlbums(): LiveData<List<AlbumWithTracks>> {
        return albumDao.getAll()
    }

    override fun getAlbum(name: String, artist: String): AlbumWithTracks? {
        return albumDao.getByNameAndArtist(name, artist)
    }

    override fun insertAlbum(album: AlbumWithTracks) {
        CoroutineScope(Dispatchers.IO).launch {
            albumDao.insert(album.album)
            album.tracks.forEach { track ->
                trackDao.insert(track)
            }
        }
    }

    override fun updateAlbum(album: AlbumWithTracks) {
        CoroutineScope(Dispatchers.IO).launch {
            albumDao.update(album.album)
            trackDao.deleteByAlbumId(album.album.id)

            album.tracks.forEach { track ->
                trackDao.insert(track)
            }
        }
    }

    override fun deleteAlbum(album: AlbumWithTracks) {
        CoroutineScope(Dispatchers.IO).launch {
            albumDao.delete(album.album)
            // Tracks should be deleted automatically
        }
    }
}
