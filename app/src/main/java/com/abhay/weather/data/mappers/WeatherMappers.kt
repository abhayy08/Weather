package com.abhay.weather.data.mappers

import com.abhay.weather.data.remote.WeatherDto2
import com.abhay.weather.domain.weather.CurrentWeatherDetails
import com.abhay.weather.domain.weather.WeatherInfo

fun WeatherDto2.toWeatherInfo(): WeatherInfo {
    val weatherDec = currentConditions.conditions
    val temp = currentConditions.temp
    val feelsLike = currentConditions.feelslike
    val pressure = currentConditions.pressure
    val humidity = currentConditions.humidity
    val windSpeed = currentConditions.windspeed
    val sunrise = currentConditions.sunrise
    val sunset = currentConditions.sunset
    val currentWeatherSummary = days[0].description
    val listOfDays = days
    val currentHourlyForecast = days[0].hours
    val currentWeather = CurrentWeatherDetails(
        weatherDec,
        temp,
        feelsLike,
        pressure,
        humidity,
        windSpeed,
        sunrise,
        sunset,
        currentWeatherSummary,
    )
    return WeatherInfo(
        currentWeatherData = currentWeather,
        weatherForecastDetails = listOfDays,
        currentHourlyForecast = currentHourlyForecast,
        locationName = ""
    )

}