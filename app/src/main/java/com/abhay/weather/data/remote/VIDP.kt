package com.abhay.weather.data.remote

data class VIDP(
    val contribution: Int,
    val distance: Int,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val quality: Int,
    val useCount: Int
)