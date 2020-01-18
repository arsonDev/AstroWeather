package pl.arsonproject.astroweather.ui.moonPhase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import pl.arsonproject.astroweather.utils.toBestDate
import pl.arsonproject.astroweather.utils.toBestTime
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.round

@RequiresApi(Build.VERSION_CODES.O)
class PhaseMoonViewModel : ViewModel() {

    var latitude = MutableLiveData<Double>()
    var longitude = MutableLiveData<Double>()

    val localDeviceTime = ObservableField<String>("")
    val sunRise = ObservableField<String>()
    val sunRiseAzimut = ObservableDouble()
    val sunSet = ObservableField<String>()
    val sunSetAzimut = ObservableDouble()
    val sunTwilight = ObservableField<String>()
    val sunDaylight = ObservableField<String>()

    val moonRise = ObservableField<String>()
    val moonSet = ObservableField<String>()
    val moonNew = ObservableField<String>()
    val moonFull = ObservableField<String>()


    init {
        Thread({
            while (true) {
                Thread.sleep(1000)
                localDeviceTime.set(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString())
            }
        }).start()
    }

    fun useAstro() {
        var astroLocation = AstroCalculator.Location(latitude.value ?: 0.0, longitude.value ?: 0.0)
        var localTime = LocalDateTime.now()
        var astroDateTime = AstroDateTime(
            localTime?.year!!,
            localTime?.month.ordinal!!,
            localTime?.dayOfMonth!!,
            localTime?.hour!!,
            localTime?.minute!!,
            localTime?.second!!,
            (localTime?.atZone(
                ZoneId.systemDefault()
            )?.offset?.totalSeconds!! / 3600),
            true
        )

        val astro = AstroCalculator(astroDateTime, astroLocation)

        sunRise.set(astro.sunInfo.sunrise.toBestTime())
        sunSet.set(astro.sunInfo.sunset.toBestTime())
        sunRiseAzimut.set(
            round(astro.sunInfo.azimuthRise)
        )
        sunSetAzimut.set(
            round(astro.sunInfo.azimuthSet)
        )
        sunTwilight.set(astro.sunInfo.twilightEvening.toBestTime())
        sunDaylight.set(astro.sunInfo.twilightMorning.toBestTime())

        moonRise.set(astro.moonInfo.moonrise.toBestTime())
        moonSet.set(astro.moonInfo.moonset.toBestTime())

        moonFull.set(astro.moonInfo.nextFullMoon.toBestDate())
        moonNew.set(astro.moonInfo.nextNewMoon.toBestDate())
    }
}