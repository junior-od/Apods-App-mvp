package com.example.mvpplayaround.ui.poddetails

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.ui.base.BaseContract

interface PodsDetailsContract {

    interface View: BaseContract.View {
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun addFavorite(astronomyPicture: AstronomyPicture)
        fun removeFavorite(astronomyPicture: AstronomyPicture)
    }

}