package com.example.seraqchove.data.entities.api.countriesnow

import com.example.seraqchove.data.entities.api.countriesnow.Cities

data class Countries (
    val error: Boolean,
    val msg: String,
    val data: List<Cities>
)