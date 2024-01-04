package com.example.mad_android.data.station

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Query("SELECT * FROM stations")
    fun getAll(): Flow<dbStation?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStations(stations: dbStation)

    @Query("DELETE FROM stations")
    suspend fun deleteAll()

    @Transaction
    suspend fun insertAll(stations: dbStation) {
        deleteAll()
        insertStations(stations)
    }
}