package com.example.seraqchove.data.repositories

import com.example.seraqchove.data.entities.api.countriesnow.Countries
import com.example.seraqchove.data.interfaces.api.CountriesnowInstance

class CountriesnowRepository {

    suspend fun getCountries() : Countries {
        return CountriesnowInstance.api.getCountries()
    }
}