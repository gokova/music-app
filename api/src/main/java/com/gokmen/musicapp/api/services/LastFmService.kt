package com.gokmen.musicapp.api.services

import com.gokmen.musicapp.api.ApiConstants
import com.gokmen.musicapp.api.models.AlbumInfoResponse
import com.gokmen.musicapp.api.models.AlbumResponse
import com.gokmen.musicapp.api.models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface LastFmService {

    @GET("?method=artist.search&api_key=${ApiConstants.API_KEY}&format=json")
    fun searchArtist(@Query("artist") artistName: String): Call<SearchResponse>

    @GET("?method=artist.getTopAlbums&api_key=${ApiConstants.API_KEY}&limit=${ApiConstants.TOP_ALBUM_LIMIT}&format=json")
    fun getTopAlbums(@Query("artist") artistName: String): Call<AlbumResponse>

    @GET("?method=album.getinfo&api_key=${ApiConstants.API_KEY}&format=json")
    fun getAlbumInfo(
        @Query("artist") artistName: String,
        @Query("album") albumName: String
    ): Call<AlbumInfoResponse>
}
