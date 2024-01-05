package com.example.mad_android.data.station

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for interacting with the local Room database
 * to perform CRUD operations on [dbStation] entities.
 */
@Dao
interface StationDao {
    /**
     * Retrieves all station information from the database as a Flow.
     *
     * @return A [Flow] emitting a nullable [dbStation] object.
     */
    @Query("SELECT * FROM stations")
    fun getAll(): Flow<dbStation?>

    /**
     * Inserts station information into the database, ignoring conflicts.
     *
     * @param stations The [dbStation] object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStations(stations: dbStation)

    /**
     * Deletes all station information from the database.
     */
    @Query("DELETE FROM stations")
    suspend fun deleteAll()

    /**
     * Inserts a [dbStation] object into the database after deleting existing data.
     *
     * @param stations The [dbStation] object to be inserted.
     */
    @Transaction
    suspend fun insertAll(stations: dbStation) {
        deleteAll()
        insertStations(stations)
    }
}