package com.example.seraqchove.data.entities.api.countriesnow

data class Countries (
    val error: Boolean,
    val msg: String,
    val data: List<Cities>
)