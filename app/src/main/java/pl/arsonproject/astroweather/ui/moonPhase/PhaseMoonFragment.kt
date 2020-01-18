package pl.arsonproject.astroweather.ui.moonPhase

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import pl.arsonproject.astroweather.R
import pl.arsonproject.astroweather.databinding.FragmentPhasemoonBinding
import pl.arsonproject.astroweather.databinding.FragmentWeatherBinding
import java.time.LocalDateTime


class PhaseMoonFragment : Fragment() {

    private lateinit var moonPhaseViewModel: PhaseMoonViewModel
    private val REQUEST_LOCATION_PERMISSION = 1002

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moonPhaseViewModel =
            ViewModelProviders.of(this).get(PhaseMoonViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentPhasemoonBinding>(
            inflater, R.layout.fragment_phasemoon, container, false
        )
        binding.vm = moonPhaseViewModel

        if (
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Display UI and wait for user interaction
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        } else {

            val locationResult: Task<Location> = LocationServices
                .getFusedLocationProviderClient(
                    requireActivity()
                )
                .lastLocation

            locationResult.addOnCompleteListener {
                val result = it.getResult()

                moonPhaseViewModel.latitude.value = result?.latitude
                moonPhaseViewModel.longitude.value = result?.longitude
            }
        }

        setObserver()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setObserver() {
        moonPhaseViewModel.longitude.observe(this, Observer {
            moonPhaseViewModel.useAstro()
        })
        moonPhaseViewModel.latitude.observe(this, Observer {
            moonPhaseViewModel.useAstro()
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val locationResult: Task<Location> = LocationServices
                        .getFusedLocationProviderClient(
                            requireActivity()
                        )
                        .lastLocation

                    locationResult.addOnCompleteListener {
                        val result = it.getResult()

                        moonPhaseViewModel.latitude.value = result?.latitude
                        moonPhaseViewModel.longitude.value = result?.longitude
                    }
                }
            }
        }
    }
}