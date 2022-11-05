package com.example.mvpplayaround.data.remote

import com.example.mvpplayaround.BuildConfig
import com.example.mvpplayaround.data.remote.models.AstronomyPictureResponse
import retrofit2.Response
import retrofit2.http.GET

interface PlanetaryService {
    /**
     * APOD - Astronomy Picture of the day.
     * See [the docs](https://api.nasa.gov/) and [github micro service](https://github.com/nasa/apod-api#docs-)
     */
    @GET("planetary/apod?count=20&api_key=${BuildConfig.API_KEY}")
    suspend fun getPictures(): Response<List<AstronomyPictureResponse>>
}