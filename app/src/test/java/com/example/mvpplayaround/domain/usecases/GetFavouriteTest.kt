package com.example.mvpplayaround.domain.usecases

import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepositoryImplTest
import com.example.mvpplayaround.util.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavouriteTest {

    lateinit var getFavourite: GetFavourite
    lateinit var favouriteDatabaseRepositoryImplTest: FavouriteDatabaseRepositoryImplTest

    @Before
    fun setUp() {
        favouriteDatabaseRepositoryImplTest = FavouriteDatabaseRepositoryImplTest()
        getFavourite = GetFavourite(favouriteDatabaseRepositoryImplTest)

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

        favouriteToInsert.add(
            FavouriteAstronomyPictureEntity(
                "Zari Moon",
                "Moon brighter than diamonds",
                "2009-10-11",
                "http://someurlmoon",
                "image",
                id = 2
            )
        )

        favouriteToInsert.add(
            FavouriteAstronomyPictureEntity(
                "Aba Sun",
                "Sun brighter than diamonds",
                "2021-01-11",
                "http://someurlsun",
                "image",
                id = 3
            )
        )

        favouriteToInsert.shuffle()

        runTest {
           favouriteToInsert.forEach {
               favouriteDatabaseRepositoryImplTest.insertFavouritePod(it)
           }
        }
    }

    /**
     * ensure favourites order By title
     * is ascending order
     * */

    @Test
    fun `order favourites in ascending order by title returns correct ascending order`(){
        val result = getFavourite.invoke(Constants.PodsFilter.TITLE)

        for(i in 0..result.size - 2) (
            assertThat(result[i].title.lowercase() <= result[i+1].title.lowercase()).isTrue()
        )
    }

    /**
     * ensure favourites order By Date
     * is descending order
     * */

    @Test
    fun `order favourites in descending order by date returns correct descending order`(){
        val result = getFavourite.invoke(Constants.PodsFilter.DATE)

        for(i in 0..result.size - 2){
            assertThat(result[i].date >= result[i+1].date).isTrue()
        }
    }


    @After
    fun tearDown() {

    }
}