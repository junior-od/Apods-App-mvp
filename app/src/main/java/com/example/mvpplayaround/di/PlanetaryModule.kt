package com.example.mvpplayaround.di

import com.example.mvpplayaround.data.remote.PlanetaryService
import com.example.mvpplayaround.data.repository.PlanetaryRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanetaryModule {

    @Singleton
    @Provides
    fun providePlanetaryService(retrofit: Retrofit): PlanetaryService = retrofit
        .create(PlanetaryService::class.java)

    @Singleton
    @Provides
    fun providePlanetaryRepoImpl(planetaryService: PlanetaryService): PlanetaryRepoImpl = PlanetaryRepoImpl(planetaryService)
}