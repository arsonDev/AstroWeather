package pl.arsonproject.astroweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
class Weather(
    @Json(name = "icon") val icon: String,
    @Json(name = "code") val code: String,
    @Json(name = "description") val description: String) {
    @Transient @PrimaryKey(autoGenerate = true) var id = 1
}
