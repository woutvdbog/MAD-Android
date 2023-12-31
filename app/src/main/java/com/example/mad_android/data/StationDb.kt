package com.example.mad_android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mad_android.data.station.Converters
import com.example.mad_android.data.station.StationDao
import com.example.mad_android.data.station.dbStation

@Database(entities = [dbStation::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StationDb : RoomDatabase() {
    abstract fun stationDao(): StationDao

    companion object {
        @Volatile
        private var Instance: StationDb? = null

        fun getDatabase(context: Context): StationDb {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, StationDb::class.java, "station_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}