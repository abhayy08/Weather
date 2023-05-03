package com.abhay.weather.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.abhay.weather.data.repository.database.relations.WeatherDataWithDays


@Dao
interface WeatherDataDao {

    @Query("SELECT COUNT(*) FROM WeatherData")
    suspend fun getCount(): Int

    @Upsert
    suspend fun insertDay(day:Days)

    @Upsert
    suspend fun insertWeatherData(weatherData: WeatherData)

    @Transaction
    @Query("SELECT * FROM WeatherData")
    suspend fun getWeatherDataWithDays(): List<WeatherDataWithDays>

}