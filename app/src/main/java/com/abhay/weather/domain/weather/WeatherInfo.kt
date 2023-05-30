package com.abhay.weather.domain.weather
import com.abhay.weather.data.remote.Day
import com.abhay.weather.data.remote.Hour

data class  WeatherInfo(
    val currentWeatherData: CurrentWeatherDetails?,
    val weatherForecastDetails: List<Day>?,
    val currentHourlyForecast: List<Hour>,
    var locationName: String,
)