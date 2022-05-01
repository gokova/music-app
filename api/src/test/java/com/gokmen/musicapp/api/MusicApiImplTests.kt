package com.gokmen.musicapp.api

import com.gokmen.musicapp.api.models.Result
import com.gokmen.musicapp.api.models.SearchArtist
import com.gokmen.musicapp.api.models.SearchMatches
import com.gokmen.musicapp.api.models.SearchResponse
import com.gokmen.musicapp.api.models.SearchResults
import com.gokmen.musicapp.api.models.Status
import com.gokmen.musicapp.api.services.LastFmService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MusicApiImplTests {

    private lateinit var musicApi: MusicApiImpl

    private lateinit var lastFmService: LastFmService

    private lateinit var idleCall: Call<SearchResponse>

    @BeforeEach
    fun setUp() {
        mockLastFmService()

        musicApi = MusicApiImpl(lastFmService)
    }

    private fun mockLastFmService() {
        lastFmService = mockk()
        idleCall = mockk()

        every { idleCall.enqueue(any()) } returns Unit

        coEvery {
            lastFmService.searchArtist(any())
        } returns Calls.response(responseForEmptyResult)

        coEvery {
            lastFmService.searchArtist("johnny cash")
        } returns Calls.response(
            Response.error(400, "Bad request".toResponseBody("text/plain".toMediaTypeOrNull()))
        )

        coEvery {
            lastFmService.searchArtist("metallica")
        } returns Calls.failure(IOException("Network error"))

        coEvery {
            lastFmService.searchArtist("pink floyd")
        } returns idleCall

        coEvery {
            lastFmService.searchArtist("cher")
        } returns Calls.response(responseForCher)
    }

    @ParameterizedTest
    @MethodSource
    fun searchArtist(
        artistName: String,
        expectedResult: Result<List<SearchArtist>>
    ) = runBlocking {
        val result = musicApi.searchArtist(artistName)

        assertEquals(expectedResult, result)
    }

    /**
     * Input domain;
     * artistName => cher (Found in service),
     *               gokmen (Not found),
     *               johnny cash (Will throw HTTP 400),
     *               metallica (Will have network problem),
     *               pink floyd (Response timeout)
     */
    private fun searchArtist() = Stream.of(
        Arguments.of("cher", Result(Status.Success, listForCher)),
        Arguments.of("gokmen", Result<List<SearchArtist>>(Status.Success, listOf())),
        Arguments.of("johnny cash", Result<List<SearchArtist>>(Status.ServerError)),
        Arguments.of("metallica", Result<List<SearchArtist>>(Status.NetworkError)),
        Arguments.of("pink floyd", Result<List<SearchArtist>>(Status.NetworkError))
    )

    private val responseForEmptyResult = SearchResponse(
        SearchResults(
            SearchMatches(
                listOf()
            )
        )
    )

    private val responseForCher = SearchResponse(
        SearchResults(
            SearchMatches(
                listOf(
                    SearchArtist(id = "bfcc6d75", name = "Cher"),
                    SearchArtist(id = "48fbfb0b", name = "Cher Lloyd"),
                    SearchArtist(id = "c43d2302", name = "Sonny & Cher"),
                    SearchArtist(id = "523b52d7", name = "Blue Cheer"),
                    SearchArtist(id = "", name = "Cheer Chen")
                )
            )
        )
    )

    private val listForCher = listOf(
        SearchArtist(id = "bfcc6d75", name = "Cher"),
        SearchArtist(id = "48fbfb0b", name = "Cher Lloyd"),
        SearchArtist(id = "c43d2302", name = "Sonny & Cher"),
        SearchArtist(id = "523b52d7", name = "Blue Cheer"),
        SearchArtist(id = "", name = "Cheer Chen")
    )
}
