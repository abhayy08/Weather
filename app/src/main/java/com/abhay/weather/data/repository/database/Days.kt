package com.abhay.weather.data.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Days(
    @PrimaryKey(autoGenerate = false)
    val dayId: Int = 0,
    val weatherId: Int = 0,
    val conditions: String,
    val datetime: String,
    val description: String,
    val feelslikemax: Double,
    val feelslikemin: Double,
    val humidity: Double,
    val pressure: Double,
    val temp: Double,
    val visibility: Double,
    val windspeed: Double,
    val sunrise: String,
    val sunset: String
)
