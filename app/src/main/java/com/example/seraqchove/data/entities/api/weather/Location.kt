package com.example.seraqchove.data.entities.api.weather

import com.google.gson.annotations.SerializedName

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,

    @SerializedName("tz_id")
    val tzID: String,

    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,

    val localtime: String
)
