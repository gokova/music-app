package com.gokmen.musicapp.api

import androidx.annotation.WorkerThread

interface MusicApi {

    @WorkerThread
    suspend fun searchArtist(artistName: String): List<SearchArtist>?

    @WorkerThread
    suspend fun getTopTracks(artistName: String)
}
