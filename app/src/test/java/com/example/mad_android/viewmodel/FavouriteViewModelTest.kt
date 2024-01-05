package com.example.mad_android.viewmodel

import com.example.mad_android.data.favourite.FavouriteRepository
import com.example.mad_android.model.Favourite
import com.example.mad_android.model.StationObject
import com.example.mad_android.ui.screens.favourites.FavouriteUiState
import com.example.mad_android.ui.screens.favourites.FavouriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify


@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var favouriteRepository: FavouriteRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetFavourites_Success() {
        val favouriteViewModel = FavouriteViewModel(favouriteRepository)
        val mockFavourites = listOf(
            Favourite("1", "Station 1", "Station 1"),
            Favourite("2", "Station 2", "Station 2")
        )

        `when`(favouriteRepository.getFavourites()).thenReturn(flowOf(mockFavourites))

        favouriteViewModel.getFavourites()

        assert(favouriteViewModel.uiState == FavouriteUiState.Success(mockFavourites))
        assert(favouriteViewModel.favourites.value == mockFavourites)
    }

    @Test
    fun testGetFavourites_Error() {
        val favouriteViewModel = FavouriteViewModel(favouriteRepository)

        `when`(favouriteRepository.getFavourites()).thenThrow(RuntimeException("Error"))

        favouriteViewModel.getFavourites()

        assert(favouriteViewModel.uiState == FavouriteUiState.Error("Error loading favourites"))
    }

    @Test
    fun testAddFavourite() = runBlocking {
        val favouriteViewModel = FavouriteViewModel(favouriteRepository)
        val mockStation = StationObject("0", "0", "id", "Station", "link", "Station")

        favouriteViewModel.addFavourite(mockStation)

        verify(favouriteRepository).addFavourite(mockStation)
    }

    @Test
    fun testRemoveFavourite() = runBlocking {
        val favouriteViewModel = FavouriteViewModel(favouriteRepository)
        val mockFavourite = Favourite("0", "Station", "Station")

        favouriteViewModel.removeFavourite(mockFavourite)

        verify(favouriteRepository).removeFavourite(mockFavourite)
    }
}