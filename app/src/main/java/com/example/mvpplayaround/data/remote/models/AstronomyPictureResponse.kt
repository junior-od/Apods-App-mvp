package com.example.mvpplayaround.data.remote.models

import com.squareup.moshi.Json

data class AstronomyPictureResponse(
    @Json(name = "service_version") val serviceVersion: String,
    val title: String,
    val explanation: String,
    val date: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "hdurl") val hdUrl: String?,
    val url: String,
)
