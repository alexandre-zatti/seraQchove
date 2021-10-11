package com.example.seraqchove.data.repositories

import androidx.lifecycle.LiveData
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.interfaces.LocationDao

class LocationRepository(private val locationDao: LocationDao) {

    suspend fun createLocation(location: Location){
        locationDao.createLocation(location)
    }

    fun getLocationByUser(userId: Int): LiveData<List<Location>> {
        return locationDao.getLocationByUser(userId)
    }



}