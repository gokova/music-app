package com.gokmen.musicapp.api

import androidx.annotation.WorkerThread
import com.gokmen.musicapp.api.models.AlbumTrack
import com.gokmen.musicapp.api.models.Result
import com.gokmen.musicapp.api.models.SearchArtist
import com.gokmen.musicapp.api.models.TopAlbum

interface MusicApi {

    @WorkerThread
    suspend fun searchArtist(artistName: String): Result<List<SearchArtist>>

    @WorkerThread
    suspend fun findTopAlbums(artistName: String): Result<List<TopAlbum>>

    @WorkerThread
    suspend fun findAlbumTracks(artistName: String, albumName: String): Result<List<AlbumTrack>>
}
