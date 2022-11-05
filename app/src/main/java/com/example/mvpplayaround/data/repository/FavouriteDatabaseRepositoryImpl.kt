package com.example.mvpplayaround.data.repository

import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity
import com.example.mvpplayaround.data.local.source.FavouriteAstronomyPictureDao

class FavouriteDatabaseRepositoryImpl(private val dao: FavouriteAstronomyPictureDao): FavouriteDatabaseRepository {
    override fun getAllFavourites(): List<FavouriteAstronomyPictureEntity> {
        return dao.getAllFavourites()
    }

    override suspend fun insertFavouritePod(favouriteAstronomyPictureEntity: FavouriteAstronomyPictureEntity) {
        return dao.insertFavouritePod(favouriteAstronomyPictureEntity)
    }

    override suspend fun deleteFavouritePod(title: String ,explanation: String, mediaType: String, url: String, date: String) {
        return dao.deleteFavouritePod(title ,explanation, mediaType, url, date)
    }

}