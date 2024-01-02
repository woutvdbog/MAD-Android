package com.example.mad_android.data.favourite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(favourite: dbFavourite)

    @Delete
    suspend fun deleteFavourite(favourite: dbFavourite)

    @Query("SELECT * FROM favourites")
    fun getFavourites(): Flow<List<dbFavourite>>
}