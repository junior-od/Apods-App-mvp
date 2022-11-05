package com.example.mvpplayaround.data.repository

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.util.NetworkResource

interface PlanetaryRepo {

    suspend fun getPods(): NetworkResource<List<AstronomyPicture>>
}