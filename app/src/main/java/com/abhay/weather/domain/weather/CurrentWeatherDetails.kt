package com.abhay.weather.domain.weather

data class CurrentWeatherDetails(
    val weatherDesc: String,
    val temp : Double,
    val feelsLike : Double,
    val pressure : Int,
    val humidity : Int,
    val windSpeed: Double,
    val sunrise: Int,
    val sunset: Int,
    val locationName: String

)
