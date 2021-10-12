package com.example.seraqchove.data.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.seraqchove.data.AppDatabase
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.entities.api.countriesnow.Countries
import com.example.seraqchove.data.entities.api.weather.Weather
import com.example.seraqchove.data.repositories.CountriesnowRepository
import com.example.seraqchove.data.repositories.LocationRepository
import com.example.seraqchove.data.repositories.WeatherRepository
import com.example.seraqchove.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

class LocationViewModel (application: Application): AndroidViewModel(application) {

    private val repository: LocationRepository
    private val countrieSnowRepository : CountriesnowRepository
    private val weatherRepository : WeatherRepository

    val countrieSnowResponse : MutableLiveData<Countries> = MutableLiveData()
    val weatherResponse : MutableLiveData<List<Weather>> = MutableLiveData()

    init {
        val locationDao = AppDatabase.getDatabase(application).locationDao()
        repository = LocationRepository(locationDao)
        countrieSnowRepository = CountriesnowRepository()
        weatherRepository = WeatherRepository()
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

    fun getWeather(locations : List<Location>){
        viewModelScope.launch {
            val response = arrayListOf<Weather>()

            for(location in locations){
                val options = mapOf("key" to Constants.WEATHERAPI_KEY, "q" to location.city, "aqi" to "no")
                response.add(weatherRepository.getWeather(options))
            }
            weatherResponse.value = response
        }
    }

}