package com.abhay.weather.data.repository.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.abhay.weather.data.repository.database.Days
import com.abhay.weather.data.repository.database.WeatherData

data class WeatherDataWithDays(
    @Embedded val weatherData: WeatherData,
    @Relation(
        parentColumn = "id",
        entityColumn = "weatherId"
    )
    val days: List<Days>
)
