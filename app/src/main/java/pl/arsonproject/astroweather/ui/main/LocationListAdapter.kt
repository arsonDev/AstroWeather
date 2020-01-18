package pl.arsonproject.astroweather.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_location.view.*
import pl.arsonproject.astroweather.R
import pl.arsonproject.astroweather.model.Location
import pl.arsonproject.astroweather.ui.locationDetail.LocationDetailActivity


class LocationListAdapter(private val dataSet : List<Location>) :
    RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {

    class LocationViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // konwert na boiekt typu view
        val localeRow = layoutInflater.inflate(R.layout.item_location,parent,false)
        return LocationViewHolder(localeRow)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        var loc = dataSet.get(position)
        val name = holder.view.location

        name.text = loc.city_name

        holder.view.setOnClickListener {
            val intent = Intent(holder.view.context,LocationDetailActivity::class.java)
            val b = Bundle()
            b.putString("location", name.text.toString())
            intent.putExtras(b)
            holder.view.context.startActivity(intent)
        }
    }
}