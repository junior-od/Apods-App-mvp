package com.example.mvpplayaround.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteAstronomyPictureDao {

    @Query("SELECT * FROM FavouriteAstronomyPictureEntity")
    fun getAllFavourites(): List<FavouriteAstronomyPictureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouritePod(favouriteAstronomyPictureEntity: FavouriteAstronomyPictureEntity)

    @Query("DELETE FROM FavouriteAstronomyPictureEntity WHERE title = :title AND explanation= :explanation AND mediaType= :mediaType AND url= :url AND date= :date")
    suspend fun deleteFavouritePod(title: String ,explanation: String, mediaType: String, url: String, date: String)

}