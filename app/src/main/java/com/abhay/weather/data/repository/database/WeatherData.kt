package com.abhay.weather.data.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherData(
    @PrimaryKey(autoGenerate = false)
    val id:Int = 0,
    val locationName: String = "",
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val feelsLike: Double,
    val visibility: Int,
    val pressure: Int,
    val humidity: Double,
    val windSpeed: Double,
    val sunrise: String,
    val sunset: String,
    val currentWeatherSummary: String,
)
