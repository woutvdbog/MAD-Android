package com.example.mad_android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mad_android.data.favourite.FavouriteDao
import com.example.mad_android.data.favourite.dbFavourite
import com.example.mad_android.data.liveboard.LiveboardDao
import com.example.mad_android.data.liveboard.dbLiveboard
import com.example.mad_android.data.station.StationDao
import com.example.mad_android.data.station.dbStation

@Database(entities = [
    dbStation::class,
    dbLiveboard::class,
    dbFavourite::class
                     ], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StationDb : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun liveboardDao(): LiveboardDao
    abstract fun favouriteDao(): FavouriteDao

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