package com.example.mvpplayaround.di

import com.example.mvpplayaround.data.repository.PlanetaryRepoImpl
import com.example.mvpplayaround.domain.usecases.FavouriteDbUseCases
import com.example.mvpplayaround.ui.ouruniverse.OurUniversePresenter
import com.example.mvpplayaround.ui.poddetails.PodDetailsPresenter
import com.example.mvpplayaround.util.DispatcherProviders
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object PresenterModule {

    // This binding is "scoped".
    @Provides
    @FragmentScoped
    fun provideOurUniversePresenter(planetaryRepoImpl: PlanetaryRepoImpl, dispatchers: DispatcherProviders,  podUseCases: FavouriteDbUseCases) = OurUniversePresenter(planetaryRepoImpl, dispatchers, podUseCases)

    @Provides
    @FragmentScoped
    fun providePodDetailsPresenter(dispatchers: DispatcherProviders, podUseCases: FavouriteDbUseCases) = PodDetailsPresenter(dispatchers, podUseCases)
}