package com.abhay.weather.data.mappers

import androidx.compose.ui.text.input.DeleteSurroundingTextCommand
import com.abhay.weather.data.remote.WeatherDto2
import com.abhay.weather.domain.weather.CurrentWeatherDetails
import com.abhay.weather.domain.weather.WeatherInfo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


fun WeatherDto2.toWeatherInfo(): WeatherInfo {
    //val weatherDec = currentConditions.conditions
    val temp = currentConditions.temp
    val feelsLike = currentConditions.feelslike
    val pressure = currentConditions.pressure
    val humidity = currentConditions.humidity
    val windSpeed = currentConditions.windspeed
    val sunrise = currentConditions.sunrise
    val sunset = currentConditions.sunset
    val currentWeatherSummary = days[0].description
    val currentHourlyForecast = days[0].hours
    val tempMax = days[0].tempmax
    val tempMin = days[0].tempmin
    val visibility = days[0].visibility
    val listOfDays = days

    val currentHour = days[0].hours.find { hour->
        val now = LocalTime.now()
        val nearestHour = now.truncatedTo(ChronoUnit.HOURS)
        val formatted = nearestHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        formatted == hour.datetime
    }
    val weatherDesc = currentHour!!.conditions.toString()

    val date = days[0].datetime
    val formattedDate = LocalDate.parse(date).format(DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH))

    val currentWeather = CurrentWeatherDetails(
        dateAndDay = formattedDate,
        weatherDesc = weatherDesc,
        temp = temp,
        feelsLike = feelsLike,
        pressure = pressure,
        humidity = humidity,
        windSpeed = windSpeed,
        sunrise = sunrise,
        sunset = sunset,
        currentWeatherSummary = currentWeatherSummary,
        tempMax = tempMax,
        tempMin = tempMin,
        visibility = visibility
    )
    return WeatherInfo(
        currentWeatherData = currentWeather,
        weatherForecastDetails = listOfDays,
        currentHourlyForecast = currentHourlyForecast,
        locationName = ""
    )

}