package com.example.mvpplayaround.data.local.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity

@Database(entities = [FavouriteAstronomyPictureEntity::class], version = 1)
abstract class FavouriteAstronomyPictureDatabase: RoomDatabase() {

    abstract val favouriteAstronomyPictureDao: FavouriteAstronomyPictureDao

    companion object {
        const val DATABASE_NAME = "fav_db"
    }
}