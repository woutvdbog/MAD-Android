package com.example.mad_android.data.liveboard

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mad_android.data.station.dbStationObject
import com.example.mad_android.model.Departures
import com.example.mad_android.model.Liveboard
import com.example.mad_android.model.Platform
import com.example.mad_android.model.StationObject

/**
 * Database entity representing liveboard information stored in the local Room database.
 *
 * @property version Version information for the liveboard.
 * @property timestamp Timestamp indicating when the liveboard data was retrieved.
 * @property station Name of the station associated with the liveboard.
 * @property stationinfo Information about the station as a [dbStationObject].
 * @property departures Departures information as a [dbDepartures].
 */
@Entity(tableName = "liveboard")
data class dbLiveboard(
    @PrimaryKey
    val version: String,
    val timestamp: String,
    val station: String,
    val stationinfo: dbStationObject,
    val departures: dbDepartures
)

/**
 * Data class representing departures information stored in the local Room database.
 *
 * @property number Number of departures.
 * @property departure List of [dbDeparture] objects representing individual departures.
 */
data class dbDepartures(
    val number: Int,
    val departure: List<dbDeparture>
)

/**
 * Data class representing departure information stored in the local Room database.
 *
 * @property id Unique identifier for the departure.
 * @property delay Departure delay in seconds.
 * @property station Name of the station associated with the departure.
 * @property stationinfo Information about the station as a [dbStationObject].
 * @property time Departure time.
 * @property vehicle Vehicle information as a [dbVehicle].
 * @property platform Departure platform.
 * @property platforminfo Information about the platform.
 * @property canceled Indicates if the departure is canceled.
 * @property left Indicates if the departure has left.
 * @property isExtra Indicates if the departure is extra.
 * @property departureConnection Connection information for the departure.
 */
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

/**
 * Data class representing vehicle information stored in the local Room database.
 *
 * @property name Name of the vehicle.
 * @property shortname Short name of the vehicle.
 * @property number Vehicle number.
 * @property type Type of the vehicle.
 * @property locationX X-coordinate of the vehicle's location.
 * @property locationY Y-coordinate of the vehicle's location.
 * @property link Link to additional information about the vehicle.
 */
data class dbVehicle(
    var name: String,
    var shortname: String,
    var number: Int,
    var type: String,
    var locationX: String,
    var locationY: String,
    var link: String
)

/**
 * Converts a [dbLiveboard] object to a [Liveboard] domain object.
 *
 * @return Converted [Liveboard] domain object.
 */
fun dbLiveboard.asDomainLiveboard(): Liveboard {
    return Liveboard(
        version = version,
        timestamp = timestamp,
        station = station,
        stationinfo = stationinfo.asDomainStationObject(),
        departures = departures.asDomainDepartures()
    )
}

/**
 * Converts a [dbDepartures] object to a [Departures] domain object.
 *
 * @return Converted [Departures] domain object.
 */
fun dbDepartures.asDomainDepartures(): Departures {
    return Departures(
        number = number,
        departure = departure.map {
            it.asDomainDeparture()
        }
    )
}

/**
 * Converts a [dbDeparture] object to a [Departure] domain object.
 *
 * @return Converted [Departure] domain object.
 */
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


/**
 * Converts a [dbVehicle] object to a [Vehicle] domain object.
 *
 * @return Converted [Vehicle] domain object.
 */
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

/**
 * Converts a [dbStationObject] object to a [StationObject] domain object.
 *
 * @return Converted [StationObject] domain object.
 */
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

/**
 * Converts a [Liveboard] domain object to a [dbLiveboard] object for database storage.
 *
 * @return Converted [dbLiveboard] object for database storage.
 */
fun Liveboard.asDbLiveboard(): dbLiveboard {
    return dbLiveboard(
        version = version,
        timestamp = timestamp,
        station = station,
        stationinfo = stationinfo.asDbStationObject(),
        departures = departures.asDbDepartures()
    )
}

/**
 * Converts a [Departures] domain object to a [dbDepartures] object for database storage.
 *
 * @return Converted [dbDepartures] object for database storage.
 */
fun Departures.asDbDepartures(): dbDepartures {
    return dbDepartures(
        number = number,
        departure = departure.map {
            it.asDbDeparture()
        }
    )
}

/**
 * Converts a [Departure] domain object to a [dbDeparture] object for database storage.
 *
 * @return Converted [dbDeparture] object for database storage.
 */
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

/**
 * Converts a [Vehicle] domain object to a [dbVehicle] object for database storage.
 *
 * @return Converted [dbVehicle] object for database storage.
 */
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

/**
 * Converts a [StationObject] domain object to a [dbStationObject] object for database storage.
 *
 * @return Converted [dbStationObject] object for database storage.
 */
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