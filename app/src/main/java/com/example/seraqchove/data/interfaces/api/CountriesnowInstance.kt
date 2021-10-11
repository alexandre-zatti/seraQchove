package com.example.seraqchove.data.interfaces.api

import retrofit2.Retrofit
import com.example.seraqchove.utils.Constants
import retrofit2.converter.gson.GsonConverterFactory

object CountriesnowInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.COUNTRIESNOW_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Countriesnow by lazy {
        retrofit.create(Countriesnow::class.java)
    }
}