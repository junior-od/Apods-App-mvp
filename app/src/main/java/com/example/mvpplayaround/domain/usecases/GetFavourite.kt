package com.example.mvpplayaround.domain.usecases

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepository
import com.example.mvpplayaround.domain.mappers.AstronomyPictureMapper
import com.example.mvpplayaround.util.Constants

class GetFavourite(
    private val repository: FavouriteDatabaseRepository
) {

    //decide the filter to apply when getting favourites from db
    operator fun invoke(podsFilter: Constants.PodsFilter = Constants.PodsFilter.TITLE): List<AstronomyPicture>{
        return when (podsFilter) {
            Constants.PodsFilter.TITLE ->
                AstronomyPictureMapper.mapAPodEntityToAstronomyPicture(repository.getAllFavourites()).sortedBy { it.title.lowercase() }
            Constants.PodsFilter.DATE ->
                AstronomyPictureMapper.mapAPodEntityToAstronomyPicture(repository.getAllFavourites()).sortedByDescending { it.date }
        }

    }

}