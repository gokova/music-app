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
                    if (response.isSuccessful) {
                        Timber.d("Search artist call returned response")
                        result = response.body()?.results?.matches?.artists
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Timber.e("Search artist call is not successful : $errorMessage")
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
                    if (response.isSuccessful) {
                        Timber.d("Top album call returned response")
                        result = response.body()?.topAlbums?.albums
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Timber.e("Top album call is not successful : $errorMessage")
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

    @WorkerThread
    override suspend fun findAlbumTracks(
        artistName: String,
        albumName: String
    ): Result<List<AlbumTrack>> {
        val countDownLatch = CountDownLatch(1)
        lateinit var result: Result<List<AlbumTrack>>

        Timber.d("Calling service to get album tracks")
        lastFmService.getAlbumInfo(artistName, albumName)
            .enqueue(object : Callback<AlbumInfoResponse> {
                override fun onResponse(
                    call: Call<AlbumInfoResponse>,
                    response: Response<AlbumInfoResponse>
                ) {
                    result = if (response.isSuccessful) {
                        Timber.d("Get album tracks call returned response")
                        Result(Status.Success, response.body()?.album?.trackResults?.tracks)
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Timber.e("Get album tracks call is not successful : $errorMessage")
                        Result(Status.ServerError)
                    }
                    countDownLatch.countDown()
                }

                override fun onFailure(call: Call<AlbumInfoResponse>, t: Throwable) {
                    Timber.e(t, "Get album tracks call failed")
                    result = Result(Status.NetworkError)
                    countDownLatch.countDown()
                }
            })

        countDownLatch.await(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
        Timber.d("Returning found tracks")

        return result
    }
}
