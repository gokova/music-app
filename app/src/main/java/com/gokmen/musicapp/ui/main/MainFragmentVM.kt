package com.gokmen.musicapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gokmen.musicapp.db.LocalStorage
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.utils.toAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentVM @Inject constructor(
    localStorage: LocalStorage
) : ViewModel() {

    val albums: LiveData<List<Album>> = Transformations
        .map(localStorage.getAlbums()) { list ->
            list?.map { it.toAlbum() }
        }
}
