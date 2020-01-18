package pl.arsonproject.astroweather.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.arsonproject.astroweather.R
import pl.arsonproject.astroweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.vm = viewModel
        binding.setLifecycleOwner(this)

        setUI()
    }

    private fun setUI() {
        val adapter = LocationListAdapter(viewModel.locationList.value!!)

        recycleViewLocation.adapter = adapter
        recycleViewLocation.layoutManager = LinearLayoutManager(this)

        viewModel.locationList.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
    }
}
