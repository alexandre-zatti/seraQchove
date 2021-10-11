package com.example.seraqchove.data.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.seraqchove.data.AppDatabase
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.entities.api.countriesnow.Countries
import com.example.seraqchove.data.repositories.CountriesnowRepository
import com.example.seraqchove.data.repositories.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel (application: Application): AndroidViewModel(application) {

    private val repository: LocationRepository
    private val countrieSnowRepository : CountriesnowRepository
    val countrieSnowResponse : MutableLiveData<Countries> = MutableLiveData()

    init {
        val locationDao = AppDatabase.getDatabase(application).locationDao()
        repository = LocationRepository(locationDao)
        countrieSnowRepository = CountriesnowRepository()
    }

    fun getLocationByUser(userId: Int): LiveData<List<Location>> {
        return repository.getLocationByUser(userId)
    }

    fun createLocation(location: Location){
        viewModelScope.launch(Dispatchers.IO){
            repository.createLocation(location)
        }
    }

    fun getCountries(){
        viewModelScope.launch {
            val response  = countrieSnowRepository.getCountries()
            countrieSnowResponse.value = response
        }
    }

}