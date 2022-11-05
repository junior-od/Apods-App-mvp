package com.example.mvpplayaround.data.usecases

data class FavouriteDbUseCases(
    val getFavouriteUseCase: GetFavourite,
    val deleteFavourite: DeleteFavourite,
    val insertFavourite: InsertFavourite
)
