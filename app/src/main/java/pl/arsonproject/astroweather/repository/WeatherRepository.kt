package pl.arsonproject.astroweather.repository

import pl.arsonproject.astroweather.model.Location
import pl.arsonproject.astroweather.repository.base.BaseRepository

class WeatherRepository(private val api : WeatherApi) : BaseRepository() {

    suspend fun getCurrentWeather() : List<Location> {
        val locationResponse = safeApiCall(
                call = {api.getCurrentWeatherAsync("Opoczno").await()},
                errorMessage = "Error Featching Weather"
            )

        return locationResponse?.data!!
    }
}