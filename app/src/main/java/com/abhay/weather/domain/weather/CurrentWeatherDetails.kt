package com.abhay.weather.domain.weather

data class CurrentWeatherDetails(
    val dateAndDay: String,
    val weatherDesc: String,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val feelsLike: Double,
    val visibility: Double,
    val pressure: Double,
    val humidity: Double,
    val windSpeed: Double,
    val sunrise: String,
    val sunset: String,
    val currentWeatherSummary: String,

    )
