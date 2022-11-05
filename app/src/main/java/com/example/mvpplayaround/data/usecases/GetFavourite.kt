package com.example.mvpplayaround.data.usecases

import android.util.Log
import com.example.mvpplayaround.data.mappers.AstronomyPictureMapper
import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepository
import com.example.mvpplayaround.util.Constants
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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