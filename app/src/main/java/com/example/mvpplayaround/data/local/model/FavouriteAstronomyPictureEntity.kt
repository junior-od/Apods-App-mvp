package com.example.mvpplayaround.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteAstronomyPictureEntity (
    val title: String,
    val explanation: String,
    val date: String,
    val url: String,
    val mediaType: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)