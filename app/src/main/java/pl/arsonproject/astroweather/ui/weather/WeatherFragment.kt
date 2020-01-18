package pl.arsonproject.astroweather.ui.weather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import pl.arsonproject.astroweather.R
import pl.arsonproject.astroweather.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
            ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentWeatherBinding>(
            inflater, R.layout.fragment_weather, container, false
        )

        binding.vm = weatherViewModel
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var city = arguments?.getString("location")
        if (city != null)
            weatherViewModel.localization.set(city)
        weatherViewModel.getWeather()
    }
}