package com.example.mvpplayaround.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvpplayaround.MockResponseFileReader
import com.example.mvpplayaround.TestServiceGenerator
import com.example.mvpplayaround.data.remote.PlanetaryService
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
class PlanetaryRepoImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var server: MockWebServer

    private lateinit var planetaryService: PlanetaryService

    lateinit var planetaryRepo: PlanetaryRepo

    private val responsesPath = "responses/"

    @Before
    fun setUp() {

        server = MockWebServer()

        val baseUrl = server.url("/").toString()

        planetaryService = TestServiceGenerator.getService(PlanetaryService::class.java, baseUrl)

        planetaryRepo = PlanetaryRepoImpl(planetaryService)
    }

   @After
    fun tearDown() {
        server.shutdown()
    }

    /**
     * test to retrieve list of pods is successful
     * */

    @Test
    fun `retrieve list of pods returns successful`() = runTest{

        //get response
        val responseBody = MockResponseFileReader.content("${responsesPath}PlanetarySuccessResponse.json")

        //enqueue the  fake response
        server.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        val response = planetaryRepo.getPods()

        assertThat(response.data).isNotNull()
    }

    /**
     * test throw 'Error Occurred' when invalid api key is passed
     * */
    @Test
    fun `invalid api key returns failure message`() = runTest {
        //get response
        val responseBody = MockResponseFileReader.content("${responsesPath}PlanetaryInvalidApiResponse.json")

        //enqueue the fake response
        server.enqueue(MockResponse().setResponseCode(403).setBody(responseBody))

        val response = planetaryRepo.getPods()

        assertThat(response.data).isNull()
        assertThat(response.message).isNotEmpty()
    }


}