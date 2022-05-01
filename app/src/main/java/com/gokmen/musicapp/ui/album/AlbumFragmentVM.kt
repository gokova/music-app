package com.gokmen.musicapp.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gokmen.musicapp.api.MusicApi
import com.gokmen.musicapp.api.Status
import com.gokmen.musicapp.db.LocalStorage
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.utils.toAlbum
import com.gokmen.musicapp.utils.toDbModel
import com.gokmen.musicapp.utils.toTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumFragmentVM @Inject constructor(
    private var localStorage: LocalStorage,
    private var musicApi: MusicApi
) : ViewModel() {

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> = _album

    private var fetchState: FetchState = FetchState.IDLE

    fun setAlbum(album: Album) {
        if (fetchState != FetchState.IDLE) return

        if (album.isSaved) {
            // When isSaved, it means album information is coming from database
            _album.postValue(album)
        }

        CoroutineScope(Dispatchers.Default).launch {
            fetchState = FetchState.FETCHING
            tryToFetchFromLocal(album)
            tryToFetchFromRemote(album)
            fetchState = FetchState.FETCHED
        }
    }

    fun saveAlbum() {
        CoroutineScope(Dispatchers.Default).launch {
            _album.value?.let {
                localStorage.insertAlbum(it.toDbModel())
                it.isSaved = true
                _album.postValue(it)
            }
        }
    }

    fun deleteAlbum() {
        CoroutineScope(Dispatchers.Default).launch {
            _album.value?.let {
                localStorage.deleteAlbum(it.toDbModel())
                it.isSaved = false
                _album.postValue(it)
            }
        }
    }

    private fun tryToFetchFromLocal(album: Album) {
        /**
         * When isSaved is false, it means album information is coming from network
         * Tries to find album in db and if it finds, posts db version.
         * If it can't find in db, posts network version
         */
        if (album.isSaved.not()) {
            val albumInDb = localStorage.getAlbum(album.name, album.artist)?.toAlbum()
            _album.postValue(albumInDb ?: album)
        }
    }

    private suspend fun tryToFetchFromRemote(album: Album) {
        /**
         * Tries to fetch album tracks from api
         * If shown album (in livedata) does not have tracks or tracks are changed, updates livedata
         * If shown album was fetched from db, updates album information in db
         */
        val result = musicApi.findAlbumTracks(album.artist, album.name)
        val foundTracks = result.data?.map { it.toTrack(album.id) }

        _album.value?.let {
            if (it.tracks == null ||
                (result.status == Status.Success && it.tracks != foundTracks)
            ) {
                it.tracks = foundTracks ?: listOf()
                _album.postValue(it)

                if (it.isSaved) {
                    localStorage.updateAlbum(it.toDbModel())
                }
            }
        }
    }

    private enum class FetchState {
        IDLE,
        FETCHING,
        FETCHED
    }
}
