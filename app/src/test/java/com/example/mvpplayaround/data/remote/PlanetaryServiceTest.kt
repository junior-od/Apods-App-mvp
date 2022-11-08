package com.example.mvpplayaround.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvpplayaround.MockResponseFileReader
import com.example.mvpplayaround.TestServiceGenerator
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetaryServiceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var server: MockWebServer

    lateinit var planetaryService: PlanetaryService

    private val responsesPath = "responses/"


    @Before
    fun setUp(){

        server = MockWebServer()

        val baseUrl = server.url("/").toString()

        planetaryService = TestServiceGenerator.getService(PlanetaryService::class.java , baseUrl)

    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    /**
     * test to retrieve non empty successful list of pods
     * */

    @Test
    fun `retrieve non empty list of pods return successful and expected result`() = runTest{
        //get response
        val responseBody = MockResponseFileReader.content("${responsesPath}PlanetarySuccessResponse.json")

        //enqueue the  fake response
        server.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))


        //send the request to server
        val response = planetaryService.getPictures()

        //request received by mock server
//        val request = server.takeRequest()


        //get details of request from request variable to test that the data is sent correctly as well

        assertThat(response.body()).isNotEmpty()
        assertThat(response.isSuccessful).isTrue()

    }

    /**
     *test to retrieve invalid api key message response
     */

    @Test
    fun `invalid api key failure response with message`() = runTest {
        //get response
        val responseBody = MockResponseFileReader.content("${responsesPath}PlanetaryInvalidApiResponse.json")

        //enqueue the fake response
        server.enqueue(MockResponse().setResponseCode(403).setBody(responseBody))

        //send the request to server
        val response = planetaryService.getPictures()

        //request received by mock server
//        val request = server.takeRequest()

        assertThat(response.errorBody()).isNotNull()
        assertThat(response.code()).isEqualTo(403)


    }
}