package com.example.mvpplayaround.di

import android.app.Application
import androidx.room.Room
import com.example.mvpplayaround.data.local.source.FavouriteAstronomyPictureDatabase
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepository
import com.example.mvpplayaround.data.repository.FavouriteDatabaseRepositoryImpl
import com.example.mvpplayaround.data.usecases.DeleteFavourite
import com.example.mvpplayaround.data.usecases.FavouriteDbUseCases
import com.example.mvpplayaround.data.usecases.GetFavourite
import com.example.mvpplayaround.data.usecases.InsertFavourite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideFavouriteAstronomyPictureDatabase(app: Application): FavouriteAstronomyPictureDatabase {
        return Room.databaseBuilder(app,
            FavouriteAstronomyPictureDatabase::class.java,
            FavouriteAstronomyPictureDatabase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideFavouriteDatabaseRepositoryImpl(database: FavouriteAstronomyPictureDatabase): FavouriteDatabaseRepository {
        return FavouriteDatabaseRepositoryImpl(database.favouriteAstronomyPictureDao)
    }

    @Singleton
    @Provides
    fun provideFavouriteDbUseCases(repository: FavouriteDatabaseRepository): FavouriteDbUseCases {
        return FavouriteDbUseCases(
            getFavouriteUseCase = GetFavourite(repository),
            deleteFavourite = DeleteFavourite(repository),
            insertFavourite = InsertFavourite(repository)
        )
    }


}