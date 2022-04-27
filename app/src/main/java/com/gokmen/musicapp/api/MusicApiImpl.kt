package com.gokmen.musicapp.api

import androidx.annotation.WorkerThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class MusicApiImpl @Inject constructor(
    private val lastFmService: LastFmService
) : MusicApi {

    companion object {
        private const val TIMEOUT_MILLISECONDS: Long = 5000
    }

    @WorkerThread
    override suspend fun searchArtist(artistName: String): List<SearchArtist>? {
        val countDownLatch = CountDownLatch(1)
        var result: List<SearchArtist>? = null

        Timber.d("Calling service to search artist")
        lastFmService.searchArtist(artistName)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    Timber.d("Search artist call returned response")
                    if (response.isSuccessful) {
                        result = response.body()?.results?.matches?.artists
                    }
                    countDownLatch.countDown()
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Timber.e(t, "Search artist call failed")
                    countDownLatch.countDown()
                }
            })

        countDownLatch.await(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
        Timber.d("Returning found artists")

        return result
    }

    @WorkerThread
    override suspend fun findTopAlbums(artistName: String): List<TopAlbum>? {
        val countDownLatch = CountDownLatch(1)
        var result: List<TopAlbum>? = null

        Timber.d("Calling service to find top albums")
        lastFmService.getTopAlbums(artistName)
            .enqueue(object : Callback<AlbumResponse> {
                override fun onResponse(
                    call: Call<AlbumResponse>,
                    response: Response<AlbumResponse>
                ) {
                    Timber.d("Top album call returned response")
                    if (response.isSuccessful) {
                        result = response.body()?.topAlbums?.albums
                    }
                    countDownLatch.countDown()
                }

                override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                    Timber.e(t, "Top album call failed")
                    countDownLatch.countDown()
                }
            })

        countDownLatch.await(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
        Timber.d("Returning found albums")

        return result
    }
}
