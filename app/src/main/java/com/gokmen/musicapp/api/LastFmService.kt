package com.gokmen.musicapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface LastFmService {

    @GET("?method=artist.search&api_key=${ApiConstants.API_KEY}&format=json")
    fun searchArtist(@Query("artist") artistName: String): Call<SearchResponse>

    @GET("?method=artist.getTopAlbums&api_key=${ApiConstants.API_KEY}&limit=${ApiConstants.TOP_ALBUM_LIMIT}&format=json")
    fun getTopAlbums(@Query("artist") artistName: String): Call<AlbumResponse>
}