package com.example.mvpplayaround.data.mappers

import com.example.mvpplayaround.data.local.model.FavouriteAstronomyPictureEntity
import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.data.remote.models.AstronomyPictureResponse

object AstronomyPictureMapper {

    fun mapToAstronomyPicture(responseList: List<AstronomyPictureResponse>): List<AstronomyPicture> {
        val astronomyPictureList: MutableList<AstronomyPicture> = ArrayList()

        for (response in responseList) {
            astronomyPictureList.add(
                AstronomyPicture(response.title, response.explanation,
                response.date, response.url, response.mediaType)
            )
        }

        return astronomyPictureList
    }

    fun mapAPodEntityToAstronomyPicture(apodEntity: List<FavouriteAstronomyPictureEntity>): List<AstronomyPicture> {
        val astronomyPictureList: MutableList<AstronomyPicture> = ArrayList()

        for (response in apodEntity) {
            astronomyPictureList.add(
                AstronomyPicture(response.title, response.explanation,
                response.date, response.url, response.mediaType)
            )
        }

        return astronomyPictureList
    }

}