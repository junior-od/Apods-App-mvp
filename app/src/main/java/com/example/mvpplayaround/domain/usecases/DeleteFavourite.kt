package com.example.mvpplayaround.domain.usecases

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepository

class DeleteFavourite(
    private val repository: FavouriteDatabaseRepository
) {

   suspend operator fun invoke(astronomyPicture: AstronomyPicture){

       repository.deleteFavouritePod(astronomyPicture.title,
            astronomyPicture.explanation,
            astronomyPicture.mediaType,
            astronomyPicture.url,
            astronomyPicture.date)
   }
}