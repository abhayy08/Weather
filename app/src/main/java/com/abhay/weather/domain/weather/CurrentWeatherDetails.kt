package com.abhay.weather.domain.weather

data class CurrentWeatherDetails(
    val weatherDesc: String,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Double,
    val windSpeed: Double,
    val sunrise: String,
    val sunset: String,
//    val locationName: String

)
