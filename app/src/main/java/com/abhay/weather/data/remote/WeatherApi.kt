package com.abhay.weather.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApi {

    @GET("weather?")
    suspend fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") key: String = "30569a3f3eb6ff881b848e3783dd2de8",
        @Query("units") unit: String = "metric"
        ): WeatherDto
}
