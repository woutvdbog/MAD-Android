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

/**
 * Room database for the TrainApp application, containing tables for stations, liveboard, and favorites.
 *
 * @property stationDao Data Access Object (DAO) for station entities.
 * @property liveboardDao Data Access Object (DAO) for liveboard entities.
 * @property favouriteDao Data Access Object (DAO) for favorite entities.
 */
@Database(entities = [
    dbStation::class,
    dbLiveboard::class,
    dbFavourite::class
                     ], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TrainAppDb : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun liveboardDao(): LiveboardDao
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile
        private var Instance: TrainAppDb? = null

        /**
         * Gets the singleton instance of the TrainApp database.
         *
         * @param context The application context.
         * @return The singleton instance of the TrainApp database.
         */
        fun getDatabase(context: Context): TrainAppDb {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrainAppDb::class.java, "station_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}