package pl.arsonproject.astroweather.repository

import kotlinx.coroutines.Deferred
import pl.arsonproject.astroweather.model.Location
import pl.arsonproject.astroweather.model.LocationResponse
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current")
    fun getCurrentWeatherAsync(@Query(value = "city") location: String) : Deferred<Response<LocationResponse>>
}