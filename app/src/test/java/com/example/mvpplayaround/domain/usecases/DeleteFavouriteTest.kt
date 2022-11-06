package com.example.mvpplayaround.domain.usecases

import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity
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
class DeleteFavouriteTest {

    lateinit var deleteFavourite: DeleteFavourite
    lateinit var favouriteDatabaseRepositoryImplTest: FavouriteDatabaseRepositoryImplTest

    @Before
    fun setUp() {
        favouriteDatabaseRepositoryImplTest = FavouriteDatabaseRepositoryImplTest()
        deleteFavourite = DeleteFavourite(favouriteDatabaseRepositoryImplTest)

        runTest {
            favouriteDatabaseRepositoryImplTest.insertFavouritePod( FavouriteAstronomyPictureEntity(
                "Stars",
                "Shine brighter than diamonds",
                "2022-10-11",
                "http://someurl",
                "image",
                id = 0
            )
            )
        }
    }

    /**
     * test that favourite has been
     * deleted successfully
     * */
    @Test
    fun `delete pod returns true`(){
         val pod = AstronomyPicture(
             "Stars",
             "Shine brighter than diamonds",
             "2022-10-11",
             "http://someurl",
             "image",
         )

         runTest {
             deleteFavourite.invoke(pod)
         }

        val getPods = favouriteDatabaseRepositoryImplTest.getAllFavourites()

         assertThat(getPods).doesNotContain(FavouriteAstronomyPictureEntMapper.mapToFavouriteAstronomyPictureEntity(pod))

    }

    @After
    fun tearDown() {
    }
}