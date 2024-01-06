package com.example.mad_android.data.favourite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for interacting with the local Room database to perform
 * CRUD operations on [dbFavourite] entities.
 */
@Dao
interface FavouriteDao {

    /**
     * Inserts a [dbFavourite] entity into the database. If the entity already exists, it will
     * be ignored.
     *
     * @param favourite The [dbFavourite] entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(favourite: dbFavourite)

    /**
     * Deletes a [dbFavourite] entity from the database.
     *
     * @param favourite The [dbFavourite] entity to be deleted.
     */
    @Delete
    suspend fun deleteFavourite(favourite: dbFavourite)

    /**
     * Retrieves all [dbFavourite] entities from the database as a Flow
     *
     * @return A [Flow] emitting a list of [dbFavourite] entities.
     */
    @Query("SELECT * FROM favourites")
    fun getFavourites(): Flow<List<dbFavourite>>
}