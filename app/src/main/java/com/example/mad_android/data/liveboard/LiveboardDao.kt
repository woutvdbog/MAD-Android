package com.example.mad_android.data.liveboard

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LiveboardDao {
    @Query("SELECT * FROM liveboard")
    fun getLiveboard(): Flow<dbLiveboard>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLiveboard(liveboard: dbLiveboard)

    @Query("DELETE FROM liveboard")
    suspend fun deleteAll()

    @Transaction
    suspend fun insertAll(liveboard: dbLiveboard) {
        deleteAll()
        insertLiveboard(liveboard)
    }

}