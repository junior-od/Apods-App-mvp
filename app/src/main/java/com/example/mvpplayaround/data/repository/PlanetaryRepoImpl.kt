package com.example.mvpplayaround.data.repository

import com.example.mvpplayaround.domain.mappers.AstronomyPictureMapper
import com.example.mvpplayaround.data.remote.PlanetaryService
import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.util.NetworkResource
import javax.inject.Inject

class PlanetaryRepoImpl @Inject constructor(private val planetaryService: PlanetaryService): PlanetaryRepo {


    override suspend fun getPods(): NetworkResource<List<AstronomyPicture>> {
        return try {
            val response = planetaryService.getPictures()
            val result = response.body()

            if (response.isSuccessful && result != null) {
                val resultMapper = AstronomyPictureMapper.mapToAstronomyPicture(result)
                val sanitizeImagesResult = resultMapper.filter { pod -> pod.mediaType == "image" }

                sanitizeImagesResult.let {
                    NetworkResource.Success(it)
                }

            } else {
                NetworkResource.Error("Error Occurred")
            }

        } catch (e: Exception) {
            NetworkResource.Error(e.message ?: "An Error Occurred")
        }
    }


}