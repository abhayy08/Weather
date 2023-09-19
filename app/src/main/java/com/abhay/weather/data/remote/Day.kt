package com.abhay.weather.data.remote

data class Day(
    val conditions: String,
    val datetime: String,
    val description: String,
    val feelslike: Double,
    val feelslikemax: Double,
    val feelslikemin: Double,
    val hours: List<Hour>,
    val humidity: Double,
    val precip: Double?,
    val precipprob: Double?,
    val pressure: Double,
    val sunrise: String,
    val sunset: String,
    val temp: Double,
    val uvindex: Int,
    val visibility: Double,
    val windspeed: Double
)