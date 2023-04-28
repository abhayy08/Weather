package com.abhay.weather.data.mappers

import com.abhay.weather.data.remote.WeatherDto
import com.abhay.weather.domain.weather.CurrentWeatherDetails
import com.abhay.weather.domain.weather.WeatherData
import com.abhay.weather.domain.weather.WeatherInfo

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDec = weather[0].description!!
    val temp = main!!.temp
    val feelsLike = main!!.feelsLike
    val pressure = main!!.pressure
    val humidity = main!!.humidity
    val windSpeed = wind!!.speed
    val sunrise = sys!!.sunrise
    val sunset = sys!!.sunset
    val name = name!!
    val currentWeather = CurrentWeatherDetails(
        weatherDec,
        temp,
        feelsLike,
        pressure,
        humidity,
        windSpeed,
        sunrise,
        sunset,
        name
    )


    return WeatherInfo(
        currentWeatherData = currentWeather
    )

}