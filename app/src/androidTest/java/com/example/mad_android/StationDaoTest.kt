package com.example.mad_android

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mad_android.data.StationDb
import com.example.mad_android.data.station.StationDao
import com.example.mad_android.data.station.asDbStation
import com.example.mad_android.data.station.asDomainStation
import com.example.mad_android.model.Station
import com.example.mad_android.model.StationObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StationDaoTest {
    private lateinit var stationDao: StationDao
    private lateinit var database: StationDb

    private var stations1 = Station(
        "1.2",
        "1704316190",
        List<StationObject>(
            1
        ) {
            StationObject(
                "4.32571361",
                "51.2191923",
                "BE.NMBS.000000101",
                "Zwijndrecht Dorp",
                "http://irail.be/stations/NMBS/000000101",
                "Zwijndrecht Dorp"
            )
        }
    )

    private suspend fun insertStationToDb() {
        stationDao.insertStations(stations1.asDbStation())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        database = Room.inMemoryDatabaseBuilder(context, StationDb::class.java)
            .allowMainThreadQueries()
            .build()
        stationDao = database.stationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsertStations_insertsStationsIntoDb() = runBlocking {
        insertStationToDb()
        val stations = stationDao.getAll().first()
        assertEquals(stations.asDomainStation(), stations1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteAll_deletesAllStationsFromDb() = runBlocking {
        insertStationToDb()
        stationDao.deleteAll()
        val stations = stationDao.getAll().first()
        assertEquals(stations, null)
    }
}