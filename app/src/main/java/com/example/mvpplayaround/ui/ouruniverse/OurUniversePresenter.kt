package com.example.mvpplayaround.ui.ouruniverse

import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.data.repository.PlanetaryRepoImpl
import com.example.mvpplayaround.domain.usecases.FavouriteDbUseCases
import com.example.mvpplayaround.ui.base.BasePresenter
import com.example.mvpplayaround.util.Constants
import com.example.mvpplayaround.util.DispatcherProviders
import com.example.mvpplayaround.util.NetworkResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//pass the linkage for view and presenter and the linkage for model and presenter
class OurUniversePresenter @Inject constructor(private val  planetaryRepoImpl: PlanetaryRepoImpl, private val dispatchers: DispatcherProviders, private val   podUseCases: FavouriteDbUseCases): BasePresenter<OurUniverseContract.View>(), OurUniverseContract.Presenter {

    private var getPodsJob : Job? = null

    private var filterBy: Constants.PodsFilter = Constants.PodsFilter.TITLE

    private var tempLatestPods: MutableList<AstronomyPicture> = ArrayList()

    private fun getFavourites(): List<AstronomyPicture> {
        return podUseCases.getFavouriteUseCase(filterBy).toMutableList()
    }

    private fun addFilter(list: List<AstronomyPicture>, filterSet: Constants.PodsFilter): List<AstronomyPicture>{
        return when (filterSet) {
            Constants.PodsFilter.TITLE -> {
                list.sortedBy { it.title.lowercase() }
            }

            Constants.PodsFilter.DATE -> {
                list.sortedByDescending { it.date }
            }
        }
    }

    private fun getLatestAndFavourites(): List<List<AstronomyPicture>>{
        val tempFavouritePods = getFavourites()
        val latestAndFavourites:  MutableList<List<AstronomyPicture>> = ArrayList()
        //pop favourites if it exists in the data list from api
        val filterUnFavouriteLatest = tempLatestPods.filter { item -> !tempFavouritePods.contains(item) }

        //add filter sort value on the list
        val latestFilterSort = addFilter(filterUnFavouriteLatest, filterBy)
        val favouriteFilterSort = addFilter(tempFavouritePods, filterBy)

        latestAndFavourites.add(latestFilterSort)
        latestAndFavourites.add(favouriteFilterSort)

        return latestAndFavourites
    }

    private suspend fun updateLatestAndFavouritesState() {
        val latestAndFavourites = getLatestAndFavourites()

        withContext(dispatchers.main){
            /* index 0 returns latest list
           *  index 1 returns favourites list */
            latestAndFavourites.let {
                view?.getLatestPodsAndFavouritePods(it[0], it[1])
            }
        }

    }

    override fun fetchLatestPods() {
       view?.loading(true)

       getPodsJob?.cancel()

       getPodsJob = CoroutineScope(dispatchers.io).launch {
           when (val response = planetaryRepoImpl.getPods()) {
               is NetworkResource.Error -> {

                   withContext(dispatchers.main){
                       view?.onErrorOccurred()
                   }
               }

               is NetworkResource.Success -> {
                   tempLatestPods =  response.data?.toMutableList() ?: ArrayList()

                   updateLatestAndFavouritesState()

               }
           }

       }
    }

    override fun pinFavorite() {

            getPodsJob?.cancel()

            getPodsJob = CoroutineScope(dispatchers.io).launch {
                updateLatestAndFavouritesState()
            }


    }

    override fun getFilterBy(): Constants.PodsFilter {
        return filterBy
    }

    override fun applyFilter(filter: Constants.PodsFilter) {
        filterBy = filter

        getPodsJob?.cancel()

        getPodsJob = CoroutineScope(dispatchers.io).launch {
            updateLatestAndFavouritesState()
        }
    }

}