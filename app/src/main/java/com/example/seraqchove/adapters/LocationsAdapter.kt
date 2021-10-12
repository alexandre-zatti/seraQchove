package com.example.seraqchove.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.seraqchove.R
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.entities.User
import com.example.seraqchove.data.entities.api.weather.Weather
import com.example.seraqchove.fragments.LocationsFragmentDirections
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_locations.view.*

class LocationsAdapter: RecyclerView.Adapter<LocationsAdapter.MyViewHolder>() {

    private var locationList = emptyList<Weather>()
    lateinit var currentUser : User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentLocation = locationList[position]
        holder.itemView.cidade.text = currentLocation.location.name + ", " + currentLocation.location.country
        holder.itemView.temperatura.text = currentLocation.current.tempC.toString() + "°C"
        holder.itemView.status.text = currentLocation.current.condition.text
        holder.itemView.data_hora.text = currentLocation.location.localtime
        holder.itemView.icon_img.load("https:" + currentLocation.current.condition.icon)

        holder.itemView.location_row.setOnClickListener {
            val action = LocationsFragmentDirections.actionLocationsFragmentToEditLocationFragment(currentUser)
            action.currentLocation = currentLocation.location.name
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    fun setData(location: List<Weather>){
        locationList = location
        notifyDataSetChanged()
    }
}