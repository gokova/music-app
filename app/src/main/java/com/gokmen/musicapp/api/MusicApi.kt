package com.gokmen.musicapp.api

import androidx.annotation.WorkerThread

interface MusicApi {

    @WorkerThread
    suspend fun searchArtist(artistName: String): List<SearchArtist>?

    @WorkerThread
    suspend fun findTopAlbums(artistName: String): List<TopAlbum>?

    @WorkerThread
    suspend fun findAlbumTracks(artistName: String, albumName: String): Result<List<AlbumTrack>>
}
