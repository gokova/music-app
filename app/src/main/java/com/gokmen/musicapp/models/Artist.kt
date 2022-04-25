package com.gokmen.musicapp.models

import com.gokmen.musicapp.api.SearchArtist

data class Artist(
    val id: String,
    val name: String
) {

    constructor(searchArtist: SearchArtist) : this(
        searchArtist.id,
        searchArtist.name
    )
}
