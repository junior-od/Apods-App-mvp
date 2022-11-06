package com.example.mvpplayaround.domain.usecases

import com.example.mvpplayaround.domain.mappers.FavouriteAstronomyPictureEntMapper
import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepository

class InsertFavourite(
    private val repository: FavouriteDatabaseRepository
) {

    suspend operator fun invoke(astronomyPicture: AstronomyPicture){
        val aPod = FavouriteAstronomyPictureEntMapper.mapToFavouriteAstronomyPictureEntity(astronomyPicture)
        repository.insertFavouritePod(aPod)
    }
}