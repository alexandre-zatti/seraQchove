package com.example.seraqchove.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seraqchove.R
import com.example.seraqchove.data.entities.Location
import kotlinx.android.synthetic.main.custom_row.view.*

class LocationsAdapter: RecyclerView.Adapter<LocationsAdapter.MyViewHolder>() {

    private var locationList = emptyList<Location>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentLocation = locationList[position]
        holder.itemView.cidade.text = currentLocation.city
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    fun setData(location: List<Location>){
        locationList = location
        notifyDataSetChanged()
    }
}