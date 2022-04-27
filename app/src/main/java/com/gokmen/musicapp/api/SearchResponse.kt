package com.gokmen.musicapp.api

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    val results: SearchResults?
)

data class SearchResults(
    @SerializedName("artistmatches")
    val matches: SearchMatches?
)

data class SearchMatches(
    @SerializedName("artist")
    val artists: List<SearchArtist>?
)

data class SearchArtist(
    @SerializedName("mbid")
    val id: String?,
    @SerializedName("name")
    val name: String?
)
