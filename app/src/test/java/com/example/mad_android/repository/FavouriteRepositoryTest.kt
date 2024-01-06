package com.example.mad_android.repository

import com.example.mad_android.data.favourite.CachingFavouriteRepository
import com.example.mad_android.data.favourite.FavouriteDao
import com.example.mad_android.data.favourite.asDatabaseFavourite
import com.example.mad_android.data.favourite.asDomainFavouriteList
import com.example.mad_android.data.favourite.dbFavourite
import com.example.mad_android.model.Favourite
import com.example.mad_android.model.StationObject
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify

class FavouriteRepositoryTest {
    private lateinit var favouriteDao: FavouriteDao
    private lateinit var cachingFavoriteRepository: CachingFavouriteRepository

    @Before
    fun setup() {
        favouriteDao = mock(FavouriteDao::class.java)
        cachingFavoriteRepository = CachingFavouriteRepository(favouriteDao)
    }

    @Test
    fun testGetFavourites() = runBlocking {
        val mockFavourites = listOf(
            dbFavourite("1", "name1", "standardname1"),
            dbFavourite("2", "name2", "standardname2"),
        )

        `when`(favouriteDao.getFavourites()).thenReturn(flowOf(mockFavourites))

        val result = cachingFavoriteRepository.getFavourites()

        result.collect { favourites ->
            assertEquals(mockFavourites.size, favourites.size)
            assertEquals(mockFavourites.asDomainFavouriteList(), favourites)
        }
    }

    @Test
    fun testAddFavourite() = runBlocking {
        val mockFavourite = StationObject(
            "4.32571361",
            "51.2191923",
            "BE.NMBS.000000101",
            "Zwijndrecht Dorp",
            "http://irail.be/stations/NMBS/000000101",
            "Zwijndrecht Dorp"
        )

        cachingFavoriteRepository.addFavourite(mockFavourite)

        verify(favouriteDao, times(1)).insertFavourite(
            dbFavourite(
                mockFavourite.id,
                mockFavourite.name,
                mockFavourite.standardname
            )
        )
    }

    @Test
    fun testRemoveFavourite() = runBlocking {
        val mockFavourite = Favourite(
            "BE.NMBS.008892254",
            "Tielt",
            "Tielt"
        )

        cachingFavoriteRepository.removeFavourite(mockFavourite)

        verify(favouriteDao, times(1)).deleteFavourite(
            mockFavourite.asDatabaseFavourite()
        )
    }
}