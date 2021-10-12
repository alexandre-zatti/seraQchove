package com.example.seraqchove.data.repositories

import androidx.lifecycle.LiveData
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.interfaces.LocationDao

class LocationRepository(private val locationDao: LocationDao) {

    suspend fun createLocation(location: Location){
        locationDao.createLocation(location)
    }

    suspend fun updateLocation(userId: Int, previous_city: String, new_city: String){
        locationDao.updateLocation(userId, previous_city, new_city)
    }

    suspend fun deleteLocation(userId: Int, city: String){
        locationDao.deleteLocation(userId, city)
    }

    fun getLocationByUser(userId: Int): LiveData<List<Location>> {
        return locationDao.getLocationByUser(userId)
    }



}