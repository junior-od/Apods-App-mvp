package com.example.mvpplayaround.data.repository

import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity

interface FavouriteDatabaseRepository {
    fun getAllFavourites(): List<FavouriteAstronomyPictureEntity>

    suspend fun insertFavouritePod(favouriteAstronomyPictureEntity: FavouriteAstronomyPictureEntity)

    suspend fun deleteFavouritePod(title: String ,explanation: String, mediaType: String, url: String, date: String)
}