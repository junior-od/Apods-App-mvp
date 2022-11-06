package com.example.mvpplayaround.data.repository

import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity

class FavouriteDatabaseRepositoryImplTest: FavouriteDatabaseRepository{

    private val favouritesAstronomyPictures = mutableListOf<FavouriteAstronomyPictureEntity>()

    override fun getAllFavourites(): List<FavouriteAstronomyPictureEntity> {
        return favouritesAstronomyPictures
    }

    override suspend fun insertFavouritePod(favouriteAstronomyPictureEntity: FavouriteAstronomyPictureEntity) {
        favouritesAstronomyPictures.add(favouriteAstronomyPictureEntity)
    }

    override suspend fun deleteFavouritePod(
        title: String,
        explanation: String,
        mediaType: String,
        url: String,
        date: String
    ) {

        //find item in the fake db
        val findPod = favouritesAstronomyPictures.filter {
            it.title.equals(title) &&
            it.explanation.equals(explanation) &&
            it.mediaType.equals(mediaType) &&
            it.url.equals(url) &&
            it.date.equals(date)
        }

        if (findPod.isNotEmpty()) {
            favouritesAstronomyPictures.remove(findPod[0])
        }
    }

}