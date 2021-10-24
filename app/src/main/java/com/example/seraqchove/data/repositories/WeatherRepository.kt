package com.example.seraqchove.data.repositories

import com.example.seraqchove.data.entities.api.weather.Weather
import com.example.seraqchove.data.interfaces.api.weather.WeatherInstance

class WeatherRepository {

    suspend fun getWeather(options : Map<String,String>) : Weather {
        return WeatherInstance.api.getWeather(options)
    }
}