package com.gokmen.musicapp.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gokmen.musicapp.api.MusicApi
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.models.Artist
import com.gokmen.musicapp.utils.toAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistFragmentVM @Inject constructor(
    private var musicApi: MusicApi
) : ViewModel() {

    var artist: Artist? = null

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    private var fetchState: FetchState = FetchState.IDLE

    fun findTopAlbums(artist: Artist) {
        if (fetchState != FetchState.IDLE) return

        CoroutineScope(Dispatchers.Default).launch {
            fetchState = FetchState.FETCHING
            val foundAlbums = musicApi.findTopAlbums(artist.name).data?.map { it.toAlbum() }
            fetchState = FetchState.FETCHED
            _albums.postValue(foundAlbums ?: listOf())
        }
    }

    private enum class FetchState {
        IDLE,
        FETCHING,
        FETCHED
    }
}
