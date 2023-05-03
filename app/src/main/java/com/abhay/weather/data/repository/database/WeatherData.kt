package com.abhay.weather.data.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherData(
    @PrimaryKey(autoGenerate = false)
    val id:Int = 0,
    val locationName: String = ""
)
