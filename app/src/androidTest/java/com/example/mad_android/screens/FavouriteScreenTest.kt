package com.example.mad_android.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.mad_android.data.favourite.FavouriteRepository
import com.example.mad_android.model.Favourite
import com.example.mad_android.model.StationObject
import com.example.mad_android.ui.screens.favourites.FavouriteViewModel
import com.example.mad_android.ui.screens.favourites.FavouritesScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavouriteScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var favouriteViewModel: FavouriteViewModel

    @Before
    fun setUp() {
        favouriteViewModel = FavouriteViewModel(FakeFavouriteRepository(
            mutableListOf(
                Favourite("1", "Station 1", "Station 1"),
                Favourite("2", "Station 2", "Station 2")
            )
        ))

        composeTestRule.setContent {
            FavouritesScreen(
                favouriteViewModel = favouriteViewModel
            )
        }
    }

    @Test
    fun favouriteScreen_displaysFavourites() {
        composeTestRule.onNodeWithText("Station 1").assertExists()
        composeTestRule.onNodeWithText("Station 2").assertExists()
    }
}

class FakeFavouriteRepository(
    private val favourites: MutableList<Favourite> = mutableListOf()
): FavouriteRepository {
    override fun getFavourites(): Flow<List<Favourite>> = flow {
        emit(favourites)
    }

    override suspend fun addFavourite(station: StationObject) {
        favourites.add(Favourite(station.id, station.name, station.standardname))
    }

    override suspend fun removeFavourite(favourite: Favourite) {
        favourites.remove(favourite)
    }
}