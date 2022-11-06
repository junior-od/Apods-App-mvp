package com.example.mvpplayaround.domain.usecases

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepositoryImplTest
import com.example.mvpplayaround.domain.mappers.FavouriteAstronomyPictureEntMapper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class InsertFavouriteTest {

    lateinit var insertFavourite: InsertFavourite
    lateinit var favouriteDatabaseRepositoryImplTest: FavouriteDatabaseRepositoryImplTest

    @Before
    fun setUp() {
        favouriteDatabaseRepositoryImplTest = FavouriteDatabaseRepositoryImplTest()
        insertFavourite = InsertFavourite(favouriteDatabaseRepositoryImplTest)
    }

    /**
     * test favourite pod has
     * been inserted successfully
     * */

    @Test
    fun `insert favourite pod and return true if successful`(){
        val pod = AstronomyPicture(
            "Stars",
            "Shine brighter than diamonds",
            "2022-10-11",
            "http://someurl",
            "image")

        runTest {
            insertFavourite.invoke(pod)
        }

        val result = favouriteDatabaseRepositoryImplTest.getAllFavourites()
        assertThat(result).contains(FavouriteAstronomyPictureEntMapper.mapToFavouriteAstronomyPictureEntity(pod))
    }

    @After
    fun tearDown() {
    }
}