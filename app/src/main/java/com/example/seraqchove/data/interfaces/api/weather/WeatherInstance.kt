package com.example.seraqchove.data.interfaces.api.weather

import com.example.seraqchove.data.interfaces.api.countriesnow.Countriesnow
import com.example.seraqchove.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.WEATHERAPI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Weather by lazy {
        retrofit.create(Weather::class.java)
    }
}