package pl.arsonproject.astroweather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "city_name") val city_name : String,
    @Json(name = "temp") var temp : Float,
    @Json(name = "app_temp") var appTemp : Float,
    @Json(name = "sunrise") var sunrise : String,
    @Json(name = "sunset") var sunset : String,
    @Json(name = "slp") var pressureOnSeaLevel : Float,
    @Json(name = "pres") var pressure : Float,
    @Json(name = "clouds") var clouds : Int,
    @Json(name = "precip") var rainPerHour : String,
    @Json(name = "snow") var snowPerHour : String,
    @Json(name = "weather") var weather : Weather
){
    @Transient @PrimaryKey(autoGenerate = true) var id = 1
}