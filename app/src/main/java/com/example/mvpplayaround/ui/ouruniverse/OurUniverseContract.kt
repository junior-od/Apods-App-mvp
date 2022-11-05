package com.example.mvpplayaround.ui.ouruniverse

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.ui.base.BaseContract
import com.example.mvpplayaround.util.Constants

//link between the presenter and the view
interface OurUniverseContract {

    interface View: BaseContract.View {
        fun loading(isLoading: Boolean)
        fun getLatestPodsAndFavouritePods( latestPods: List<AstronomyPicture>, favouritePods: List<AstronomyPicture>)
        fun onErrorOccurred()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun fetchLatestPods()
        fun pinFavorite()
        fun getFilterBy(): Constants.PodsFilter
        fun applyFilter(filter: Constants.PodsFilter)
    }


}