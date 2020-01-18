package pl.arsonproject.astroweather.ui.weather

import android.app.Application
import android.widget.Toast
import androidx.databinding.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.arsonproject.astroweather.repository.ApiFactory
import pl.arsonproject.astroweather.ui.utils.Setting

class WeatherViewModel(application: Application) : AndroidViewModel(application), Observable {

    @Bindable
    // todo: pobrac aktualna lokalizacje
    val localization: ObservableField<String> = ObservableField("Opoczno")

    val cityName: ObservableField<String> = ObservableField()
    var temp: ObservableFloat = ObservableFloat()
    var feelTemp = ObservableFloat()
    var weather = ObservableField<String>()
    var clods = ObservableInt()
    var rain = ObservableField<String>()
    var snow = ObservableField<String>()
    var pressure = ObservableFloat()
    var sunrise = ObservableField<String>()
    var sunset = ObservableField<String>()
    var iconRes = ObservableInt()
    var tempUnit = ObservableField<String>()

    init {
        GetUnitSetting()
    }

    private fun GetUnitSetting() {
        tempUnit.set(Setting.tempUnit)
        
    }

    fun getWeather() {
        try {
            viewModelScope.launch {
                var weatherService = ApiFactory.weatherApi
                var response = weatherService.getCurrentWeatherAsync(localization.get()!!).await()
                if (response.isSuccessful) {
                    cityName.set(response.body()?.data?.first()?.city_name)
                    temp.set(response.body()?.data?.first()?.temp ?: 0F)
                    feelTemp.set(response.body()?.data?.first()?.appTemp ?: 0F)
                    weather.set(response.body()?.data?.first()?.weather?.description)
                    clods.set(response.body()?.data?.first()?.clouds ?: -1)
                    rain.set("%.2f".format(response.body()?.data?.first()?.rainPerHour?.toFloat() ?: 0F))
                    snow.set("%.2f".format(response.body()?.data?.first()?.snowPerHour?.toFloat() ?: 0F))
                    sunrise.set(response.body()?.data?.first()?.sunrise)
                    sunset.set(response.body()?.data?.first()?.sunset)
                    pressure.set(response.body()?.data?.first()?.pressure ?: 0F)
                    iconRes.set(
                        getApplication<Application>().resources.getIdentifier(
                            response.body()?.data?.first()?.weather?.icon,
                            "drawable",
                            getApplication<Application>().packageName
                        )
                    )
                } else
                    Toast.makeText(
                        getApplication(),
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    )
            }
        } catch (e: Exception) {
            Toast.makeText(
                getApplication<Application>().applicationContext,
                "Nie można pobrać danych dla tej lokalizacji : ${e.message}",
                Toast.LENGTH_LONG
            )
        }
    }

    @delegate:Transient
    private val mCallBacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.add(callback)
    }
}