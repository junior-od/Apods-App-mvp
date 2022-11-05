package com.example.mvpplayaround.data.mappers

import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity
import com.example.mvpplayaround.data.remote.models.AstronomyPicture

object FavouriteAstronomyPictureEntMapper {

    fun mapToFavouriteAstronomyPictureEntity(astronomyPicture: AstronomyPicture): FavouriteAstronomyPictureEntity {
        return FavouriteAstronomyPictureEntity(
            astronomyPicture.title,
            astronomyPicture.explanation,
            astronomyPicture.date,
            astronomyPicture.url,
            astronomyPicture.mediaType,
        )
    }

}