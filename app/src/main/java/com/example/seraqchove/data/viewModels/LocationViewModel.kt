package com.example.seraqchove.data.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.seraqchove.data.AppDatabase
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.repositories.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel (application: Application): AndroidViewModel(application) {

    private val repository: LocationRepository

    init {
        val locationDao = AppDatabase.getDatabase(application).locationDao()
        repository = LocationRepository(locationDao)
    }

    fun getLocationByUser(userId: Int): LiveData<List<Location>> {
        return repository.getLocationByUser(userId)
    }

    fun createUser(location: Location){
        viewModelScope.launch(Dispatchers.IO){
            repository.createLocation(location)
        }
    }
}