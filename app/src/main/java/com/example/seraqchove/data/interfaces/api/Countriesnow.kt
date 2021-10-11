package com.example.seraqchove.data.interfaces.api

import com.example.seraqchove.data.entities.api.countriesnow.Countries
import retrofit2.http.GET

interface Countriesnow {
    @GET("countries")
    suspend fun getCountries() : Countries
}