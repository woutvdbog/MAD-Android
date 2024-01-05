package com.example.mad_android.data.favourite

import com.example.mad_android.model.Favourite
import com.example.mad_android.model.StationObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository interface for managing the operations related to favorite stations.
 */
interface FavouriteRepository {

    /**
     * Retrieves a Flow of favourite stations from the repository.
     *
     * @return A [Flow] emitting a list of [Favourite] station.
     */
    fun getFavourites(): Flow<List<Favourite>>

    /**
     * Adds a [StationObject] as a favorite item to the repository.
     *
     * @param station The [StationObject] to be added as a favorite item.
     */
    suspend fun addFavourite(station: StationObject)

    /**
     * Removes a [Favourite] item from the repository.
     *
     * @param favourite The [Favourite] item to be removed.
     */
    suspend fun removeFavourite(favourite: Favourite)
}

/**
 * Implementation of the [FavouriteRepository] interface that caches favorite items
 * using a local Room database.
 *
 * @param favouriteDao The Data Access Object (DAO) for favorite items.
 */
class CachingFavouriteRepository(
    private val favouriteDao: FavouriteDao
) : FavouriteRepository {

    /**
     * Retrieves a Flow of favorite items from the local Room database, mapping
     * them to domain models.
     *
     * @return A [Flow] emitting a list of [Favourite] domain models.
     */
    override fun getFavourites(): Flow<List<Favourite>> {
        return favouriteDao.getFavourites().map {favourites ->
            favourites.map {
                it.asDomainFavourite()
            }
        }
    }

    /**
     * Adds a [StationObject] as a favorite item to the local Room database.
     *
     * @param station The [StationObject] to be added as a favorite item.
     */
    override suspend fun addFavourite(station: StationObject) {
        val favourite = dbFavourite(
            id = station.id,
            name = station.name,
            standardname = station.standardname
        )
        favouriteDao.insertFavourite(favourite)
    }

    /**
     * Removes a [Favourite] item from the local Room database.
     *
     * @param favourite The [Favourite] item to be removed.
     */
    override suspend fun removeFavourite(favourite: Favourite) {
        favouriteDao.deleteFavourite(favourite.asDatabaseFavourite())
    }
}