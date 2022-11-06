package com.example.mvpplayaround.ui.poddetails

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.domain.usecases.FavouriteDbUseCases
import com.example.mvpplayaround.ui.base.BasePresenter
import com.example.mvpplayaround.util.DispatcherProviders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class PodDetailsPresenter @Inject constructor(private val dispatchers: DispatcherProviders,
                                              private val podUseCases: FavouriteDbUseCases
): BasePresenter<PodsDetailsContract.View>(), PodsDetailsContract.Presenter {


    private var addJob : Job? = null
    private var removeJob : Job? = null

    override fun addFavorite(astronomyPicture: AstronomyPicture) {
        addJob?.cancel()

        addJob = CoroutineScope(dispatchers.io).launch {
            podUseCases.insertFavourite.invoke(astronomyPicture)
        }
    }

    override fun removeFavorite(astronomyPicture: AstronomyPicture) {
        removeJob?.cancel()

        removeJob = CoroutineScope(dispatchers.io).launch {
            podUseCases.deleteFavourite.invoke(astronomyPicture)
        }
    }

}