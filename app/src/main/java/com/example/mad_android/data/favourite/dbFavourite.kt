package com.example.mad_android.data.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mad_android.model.Favourite

/**
 * Database entity representing a favorite item stored in the local Room database.
 *
 * @property id Unique identifier for the favorite item.
 * @property name Display name of the favorite item.
 * @property standardname Standard name of the favorite item.
 */
@Entity(tableName = "favourites")
data class dbFavourite(
    @PrimaryKey
    val id: String,
    val name: String,
    val standardname: String
)



/**
 * Converts a [dbFavourite] object to a [Favourite] domain object.
 *
 * @return Converted [Favourite] domain object.
 */
fun dbFavourite.asDomainFavourite(): Favourite {
    return Favourite(
        id = id,
        name = name,
        standardname = standardname
    )
}

/**
 * Converts a list of [dbFavourite] objects to a list of [Favourite] domain objects.
 *
 * @return List of converted [Favourite] domain objects.
 */
fun List<dbFavourite>.asDomainFavouriteList(): List<Favourite> {
    return map {
        it.asDomainFavourite()
    }
}

/**
 * Converts a [Favourite] domain object to a [dbFavourite] object for database storage.
 *
 * @return Converted [dbFavourite] object.
 */
fun Favourite.asDatabaseFavourite(): dbFavourite {
    return dbFavourite(
        id = id,
        name = name,
        standardname = standardname
    )
}