package com.gokmen.musicapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gokmen.musicapp.api.MusicApi
import com.gokmen.musicapp.models.Artist
import com.gokmen.musicapp.utils.toArtist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentVM @Inject constructor(
    private var musicApi: MusicApi
) : ViewModel() {

    companion object {
        private const val MINIMUM_LENGTH = 3
    }

    private val _showNameWarning = MutableLiveData<Boolean>()
    val showNameWarning: LiveData<Boolean> = _showNameWarning

    private val _showListEmptyWarning = MutableLiveData<Boolean>()
    val showListEmptyWarning: LiveData<Boolean> = _showListEmptyWarning

    private val _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>> = _artists

    fun searchArtist(artistName: String?) {
        if (artistName.isNullOrBlank() || artistName.trim().length < MINIMUM_LENGTH) {
            _showNameWarning.postValue(true)
            return
        }

        CoroutineScope(Dispatchers.Default).launch {
            val foundArtists = musicApi.searchArtist(artistName).data?.map { it.toArtist() }
            _artists.postValue(foundArtists ?: listOf())

            if (foundArtists.isNullOrEmpty()) {
                _showListEmptyWarning.postValue(true)
            }
        }
    }

    fun nameWarningIsShown() {
        _showNameWarning.postValue(false)
    }

    fun listEmptyWarningIsShown() {
        _showListEmptyWarning.postValue(false)
    }
}
