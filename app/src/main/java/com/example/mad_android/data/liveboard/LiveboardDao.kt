package com.example.mad_android.data.liveboard

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for interacting with the local Room database
 * to perform CRUD operations on [dbLiveboard] entities.
 */
@Dao
interface LiveboardDao {

    /**
     * Retrieves liveboard information from the database as a Flow.
     *
     * @return A [Flow] emitting a [dbLiveboard] object.
     */
    @Query("SELECT * FROM liveboard")
    fun getLiveboard(): Flow<dbLiveboard>

    /**
     * Inserts or replaces liveboard information into the database.
     *
     * @param liveboard The [dbLiveboard] object to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiveboard(liveboard: dbLiveboard)

    /**
     * Deletes all liveboard information from the database.
     */
    @Query("DELETE FROM liveboard")
    suspend fun deleteAll()

    /**
     * Inserts a [dbLiveboard] object into the database after deleting existing data.
     *
     * @param liveboard The [dbLiveboard] object to be inserted.
     */
    @Transaction
    suspend fun insertAll(liveboard: dbLiveboard) {
        deleteAll()
        insertLiveboard(liveboard)
    }

}