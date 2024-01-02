package com.example.mad_android.data.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mad_android.model.Favourite

@Entity(tableName = "favourites")
data class dbFavourite(
    @PrimaryKey
    val id: String,
    val name: String,
    val standardname: String
)

fun dbFavourite.asDomainFavourite(): Favourite {
    return Favourite(
        id = id,
        name = name,
        standardname = standardname
    )
}

fun List<dbFavourite>.asDomainFavouriteList(): List<Favourite> {
    return map {
        it.asDomainFavourite()
    }
}

fun Favourite.asDatabaseFavourite(): dbFavourite {
    return dbFavourite(
        id = id,
        name = name,
        standardname = standardname
    )
}

fun List<Favourite>.asDatabaseFavouriteList(): List<dbFavourite> {
    return map {
        it.asDatabaseFavourite()
    }
}