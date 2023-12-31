package com.example.mad_android.data.liveboard

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mad_android.data.station.dbStationObject
import com.example.mad_android.model.Departures
import com.example.mad_android.model.Liveboard
import com.example.mad_android.model.Platform
import com.example.mad_android.model.StationObject

@Entity(tableName = "liveboard")
data class dbLiveboard(
    @PrimaryKey
    val version: String,
    val timestamp: String,
    val station: String,
    val stationinfo: dbStationObject,
    val departures: dbDepartures
)

data class dbDepartures(
    val number: Int,
    val departure: List<dbDeparture>
)

data class dbDeparture(
    val id: Int,
    val delay: Int,
    val station: String,
    val stationinfo: dbStationObject,
    val time: String,
    val vehicle: String,
    val vehicleinfo: dbVehicle,
    val platform: String,
    val platforminfo: Platform,
    val canceled: Int,
    val left: Int,
    val isExtra: Int,
    val departureConnection: String
)

data class dbVehicle(
    var name: String,
    var shortname: String,
    var number: Int,
    var type: String,
    var locationX: String,
    var locationY: String,
    var link: String
)

data class dbPlatform(
    var name: String,
    var normal: String,
)

fun dbLiveboard.asDomainLiveboard(): Liveboard {
    return Liveboard(
        version = version,
        timestamp = timestamp,
        station = station,
        stationinfo = stationinfo.asDomainStationObject(),
        departures = departures.asDomainDepartures()
    )
}

fun dbDepartures.asDomainDepartures(): Departures {
    return Departures(
        number = number,
        departure = departure.map {
            it.asDomainDeparture()
        }
    )
}

fun dbDeparture.asDomainDeparture(): com.example.mad_android.model.Departure {
    return com.example.mad_android.model.Departure(
        id = id,
        delay = delay,
        station = station,
        stationinfo = stationinfo.asDomainStationObject(),
        time = time,
        vehicle = vehicle,
        vehicleinfo = vehicleinfo.asDomainVehicle(),
        platform = platform,
        platforminfo = platforminfo,
        canceled = canceled,
        left = left,
        isExtra = isExtra,
        departureConnection = departureConnection
    )
}

fun dbVehicle.asDomainVehicle(): com.example.mad_android.model.Vehicle {
    return com.example.mad_android.model.Vehicle(
        name = name,
        shortname = shortname,
        number = number,
        type = type,
        locationX = locationX,
        locationY = locationY,
        link = link
    )
}

fun dbStationObject.asDomainStationObject(): StationObject {
    return StationObject(
        locationX = locationX,
        locationY = locationY,
        id = id,
        name = name,
        link = link,
        standardname = standardname
    )
}

fun Liveboard.asDbLiveboard(): dbLiveboard {
    return dbLiveboard(
        version = version,
        timestamp = timestamp,
        station = station,
        stationinfo = stationinfo.asDbStationObject(),
        departures = departures.asDbDepartures()
    )
}

fun Departures.asDbDepartures(): dbDepartures {
    return dbDepartures(
        number = number,
        departure = departure.map {
            it.asDbDeparture()
        }
    )
}

fun com.example.mad_android.model.Departure.asDbDeparture(): dbDeparture {
    return dbDeparture(
        id = id,
        delay = delay,
        station = station,
        stationinfo = stationinfo.asDbStationObject(),
        time = time,
        vehicle = vehicle,
        vehicleinfo = vehicleinfo.asDbVehicle(),
        platform = platform,
        platforminfo = platforminfo,
        canceled = canceled,
        left = left,
        isExtra = isExtra,
        departureConnection = departureConnection
    )
}

fun com.example.mad_android.model.Vehicle.asDbVehicle(): dbVehicle {
    return dbVehicle(
        name = name,
        shortname = shortname,
        number = number,
        type = type,
        locationX = locationX,
        locationY = locationY,
        link = link
    )
}

fun StationObject.asDbStationObject(): dbStationObject {
    return dbStationObject(
        locationX = locationX,
        locationY = locationY,
        id = id,
        name = name,
        link = link,
        standardname = standardname
    )
}