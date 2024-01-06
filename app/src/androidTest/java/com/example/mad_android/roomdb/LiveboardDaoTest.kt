package com.example.mad_android.roomdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mad_android.data.TrainAppDb
import com.example.mad_android.data.liveboard.LiveboardDao
import com.example.mad_android.data.liveboard.asDbLiveboard
import com.example.mad_android.data.liveboard.asDomainLiveboard
import com.example.mad_android.model.Departure
import com.example.mad_android.model.Departures
import com.example.mad_android.model.Liveboard
import com.example.mad_android.model.Platform
import com.example.mad_android.model.StationObject
import com.example.mad_android.model.Vehicle
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LiveboardDaoTest {
    private lateinit var liveboardDao: LiveboardDao
    private lateinit var database: TrainAppDb

    private var liveboard1 = Liveboard(
        "1.2",
        "1704315149",
        "Ghent-Sint-Pieters",
        StationObject(
            "3.710675",
            "51.035896",
            "BE.NMBS.008892007",
            "Ghent-Sint-Pieters",
            "http://irail.be/stations/NMBS/008892007",
            "Gent-Sint-Pieters"
        ),
        Departures(
            1,
            List<Departure>(
                1
            ) {
                Departure(
                    0,
                    0,
                    "De Panne",
                    StationObject(
                        "2.601963",
                        "51.0774",
                        "BE.NMBS.008892338",
                        "De Panne",
                        "http://irail.be/stations/NMBS/008892338",
                        "De Panne"
                    ),
                    "1704315120",
                    "BE.NMBS.IC3020",
                    Vehicle(
                        "BE.NMBS.IC3020",
                        "IC 3020",
                        3020,
                        "IC",
                        "0",
                        "0",
                        "http://irail.be/vehicle/IC3020"
                    ),
                    "7",
                    Platform(
                        "7",
                        "7",
                    ),
                    0,
                    0,
                    0,
                    "http://irail.be/connections/8892007/20240103/IC 3020"
                )
            }
        )
    )

    private suspend fun insertLiveboardToDb() {
        liveboardDao.insertAll(liveboard1.asDbLiveboard())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        database = Room.inMemoryDatabaseBuilder(context, TrainAppDb::class.java)
            .allowMainThreadQueries()
            .build()
        liveboardDao = database.liveboardDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsertLiveboard_insertsLiveboardIntoDb() = runBlocking {
        insertLiveboardToDb()
        val liveboard = liveboardDao.getLiveboard().first()
        assertEquals(liveboard.asDomainLiveboard(), liveboard1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteAll_deletesAllLiveboardsFromDb() = runBlocking {
        insertLiveboardToDb()
        liveboardDao.deleteAll()
        val liveboard = liveboardDao.getLiveboard().first()
        assertEquals(liveboard, null)
    }
}