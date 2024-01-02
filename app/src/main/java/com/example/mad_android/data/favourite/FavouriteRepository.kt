package com.example.mad_android.data.favourite

import com.example.mad_android.model.Favourite
import com.example.mad_android.model.StationObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FavouriteRepository {
    suspend fun getFavourites(): Flow<List<Favourite>>

    suspend fun addFavourite(station: StationObject)

    suspend fun removeFavourite(favourite: Favourite)
}

class CachingFavouriteRepository(
    private val favouriteDao: FavouriteDao
) : FavouriteRepository {
    private val favourites = mutableListOf<Favourite>()

    override suspend fun getFavourites(): Flow<List<Favourite>> {
        return favouriteDao.getFavourites().map {favourites ->
            favourites.map {
                it.asDomainFavourite()
            }
        }
    }

    override suspend fun addFavourite(station: StationObject) {
        val favourite = dbFavourite(
            id = station.id,
            name = station.name,
            standardname = station.standardname
        )
        favouriteDao.insertFavourite(favourite)
    }

    override suspend fun removeFavourite(favourite: Favourite) {
        favouriteDao.deleteFavourite(favourite.asDatabaseFavourite())
    }
}