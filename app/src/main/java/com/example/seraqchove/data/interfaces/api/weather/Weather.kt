package com.example.seraqchove.data.interfaces.api.weather

import com.example.seraqchove.data.entities.api.weather.Weather
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Weather {
    @GET("current.json")
    suspend fun getWeather(
        @QueryMap options : Map<String, String>
    ) : Weather
}