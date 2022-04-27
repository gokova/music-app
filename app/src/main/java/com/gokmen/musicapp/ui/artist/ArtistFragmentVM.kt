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

    private val _showListEmptyWarning = MutableLiveData<Boolean>()
    val showListEmptyWarning: LiveData<Boolean> = _showListEmptyWarning

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    fun findTopAlbums(artist: Artist) {
        CoroutineScope(Dispatchers.Default).launch {
            val foundAlbums = musicApi.findTopAlbums(artist.name)?.map { it.toAlbum() }
            _albums.postValue(foundAlbums ?: listOf())

            if (foundAlbums.isNullOrEmpty()) {
                _showListEmptyWarning.postValue(true)
            }
        }
    }

    fun listEmptyWarningIsShown() {
        _showListEmptyWarning.postValue(false)
    }
}
