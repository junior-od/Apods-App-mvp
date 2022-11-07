package com.example.mvpplayaround.ui.ouruniverse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvpplayaround.MainCoroutineRule
import com.example.mvpplayaround.MockResponseFileReader
import com.example.mvpplayaround.TestServiceGenerator
import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity
import com.example.mvpplayaround.data.remote.PlanetaryService
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepository
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepositoryImplTest
import com.example.mvpplayaround.data.repository.PlanetaryRepoImpl
import com.example.mvpplayaround.domain.usecases.DeleteFavourite
import com.example.mvpplayaround.domain.usecases.FavouriteDbUseCases
import com.example.mvpplayaround.domain.usecases.GetFavourite
import com.example.mvpplayaround.domain.usecases.InsertFavourite
import com.example.mvpplayaround.util.DispatcherProviders
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class OurUniversePresenterTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var server: MockWebServer

    private val responsesPath = "responses/"

    private lateinit var ourUniversePresenter: OurUniversePresenter

    private lateinit var dispatcherProviders: DispatcherProviders

    private lateinit var favouriteDbUseCasesTest: FavouriteDbUseCases

    lateinit var deleteFavourite: DeleteFavourite
    private lateinit var getFavourite: GetFavourite
    lateinit var insertFavourite: InsertFavourite

    private lateinit var favouriteDatabaseRepository: FavouriteDatabaseRepository

    lateinit var planetaryRepo: PlanetaryRepoImpl

    private lateinit var planetaryService: PlanetaryService

    @Mock
    private lateinit var view  : OurUniverseContract.View

    @Before
    fun setUp() {
//        MockitoAnnotations.initMocks(this)
        //mock server behaviour
        server = MockWebServer()

        val baseUrl = server.url("/").toString()

        planetaryService = TestServiceGenerator.getService(PlanetaryService::class.java, baseUrl)

        planetaryRepo = PlanetaryRepoImpl(planetaryService)


        //setup testdispatcher instance
        dispatcherProviders = object: DispatcherProviders {
            override val main: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
            override val io: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
            override val default: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
            override val unconfined: CoroutineDispatcher
                get() = TestCoroutineDispatcher()
        }


        //setup db use case

        favouriteDatabaseRepository = FavouriteDatabaseRepositoryImplTest()

        deleteFavourite = DeleteFavourite(favouriteDatabaseRepository)
        insertFavourite = InsertFavourite(favouriteDatabaseRepository)
        getFavourite = GetFavourite(favouriteDatabaseRepository)

        favouriteDbUseCasesTest = FavouriteDbUseCases(
            getFavouriteUseCase = getFavourite,
            insertFavourite = insertFavourite,
            deleteFavourite = deleteFavourite
        )

        val favouriteToInsert = mutableListOf<FavouriteAstronomyPictureEntity>()

        favouriteToInsert.add(
            FavouriteAstronomyPictureEntity(
                "Stars",
                "Shine brighter than diamonds",
                "2022-10-11",
                "http://someurl",
                "image",
                id = 1
            )
        )

        favouriteToInsert.shuffle()

        runTest {
            favouriteToInsert.forEach {
                favouriteDatabaseRepository.insertFavouritePod(it)
            }
        }

        //mock presenter instance
        ourUniversePresenter = OurUniversePresenter(
            planetaryRepo,
            dispatchers = dispatcherProviders,
            favouriteDbUseCasesTest
        )

        ourUniversePresenter.onViewCreated(view)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    /**
     * test that app fetch latest pods and favourite pods successfully
     * */

    @Test
    fun `retrieve latest pods and favourite pods successfully`() = runTest {

        //get response
        val responseBody = MockResponseFileReader.content("${responsesPath}PlanetarySuccessResponse.json")

        //enqueue the  fake response
        server.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        val result = ourUniversePresenter.getPodsFromApi()


        assertThat(result.data).isNotNull()
    }

    /**
     * test that an error occurred due to invalid api key
     * */

    @Test
    fun `an error occurred due to invalid api key`()= runBlocking {
        //get response
        val responseBody = MockResponseFileReader.content("${responsesPath}PlanetaryInvalidApiResponse.json")

        //enqueue the fake response
        server.enqueue(MockResponse().setResponseCode(403).setBody(responseBody))

        ourUniversePresenter.fetchLatestPods()

        //delay to wait for coroutine stuff to run completely
        delay(50)

        verify(view, times(1)).loading(anyBoolean())
        verify(view, times(1)).onErrorOccurred()

    }

    /**
     * test to fetch favourites and latest
     * pods and display to the view
     * */

    @Test
    fun `fetch favourites and latest pods and display to the view  is successful`() = runBlocking {
        //get response
        val responseBody = MockResponseFileReader.content("${responsesPath}PlanetarySuccessResponse.json")

        //enqueue the fake response
        server.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        ourUniversePresenter.fetchLatestPods()

        //delay to wait for coroutine stuff to run completely
        delay(50)

        verify(view, times(1)).loading(anyBoolean())
        verify(view, times(1)).getLatestPodsAndFavouritePods(anyList(), anyList())

    }

    /**
     * test to pin favourite
     * to view
     * */
    @Test
    fun `pin favourite to view is successful`() = runBlocking {
        ourUniversePresenter.pinFavorite()

        //delay to wait for coroutine stuff to run completely
        delay(50)

        verify(view, times(1)).getLatestPodsAndFavouritePods(anyList(), anyList())
    }
}