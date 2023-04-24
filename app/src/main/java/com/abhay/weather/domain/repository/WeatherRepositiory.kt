package com.abhay.weather.domain.repository

import com.abhay.weather.domain.util.Resource
import com.abhay.weather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}