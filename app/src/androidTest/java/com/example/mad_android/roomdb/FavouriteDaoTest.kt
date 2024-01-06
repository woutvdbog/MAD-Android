package com.example.mad_android.roomdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mad_android.data.TrainAppDb
import com.example.mad_android.data.favourite.FavouriteDao
import com.example.mad_android.data.favourite.asDatabaseFavourite
import com.example.mad_android.data.favourite.asDomainFavourite
import com.example.mad_android.model.Favourite
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavouriteDaoTest {
    private lateinit var favouriteDao: FavouriteDao
    private lateinit var database: TrainAppDb

    private var favourite1 = Favourite("BE.NMBS.008892254", "Tielt", "Tielt")
    private var favourite2 = Favourite("BE.NMBS.008892007", "Ghent-Sint-Pieters", "Gent-Sint-Pieters")

    private suspend fun insertFavouriteToDb() {
        favouriteDao.insertFavourite(favourite1.asDatabaseFavourite())
    }

    private suspend fun insertTwoTasksToDb() {
        favouriteDao.insertFavourite(favourite1.asDatabaseFavourite())
        favouriteDao.insertFavourite(favourite2.asDatabaseFavourite())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        database = Room.inMemoryDatabaseBuilder(context, TrainAppDb::class.java)
            .allowMainThreadQueries()
            .build()
        favouriteDao = database.favouriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsertFavourite_insertsFavouriteIntoDb() = runBlocking {
        insertFavouriteToDb()
        val favourites = favouriteDao.getFavourites().first()
        assertEquals(favourites[0].asDomainFavourite(), favourite1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetFavourites_returnsAllFavouritesFromDb() = runBlocking {
        insertTwoTasksToDb()
        val favourites = favouriteDao.getFavourites().first()
        assertEquals(favourites[0].asDomainFavourite(), favourite1)
        assertEquals(favourites[1].asDomainFavourite(), favourite2)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteFavourite_deletesFavouriteFromDb() = runBlocking {
        insertTwoTasksToDb()
        favouriteDao.deleteFavourite(favourite1.asDatabaseFavourite())
        val favourites = favouriteDao.getFavourites().first()
        assertEquals(favourites[0].asDomainFavourite(), favourite2)
    }
}