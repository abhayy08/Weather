package com.abhay.weather.data.mappers

import com.abhay.weather.data.remote.Day
import com.abhay.weather.data.remote.WeatherDto2
import com.abhay.weather.data.repository.database.Days
import com.abhay.weather.data.repository.database.relations.WeatherDataWithDays
import com.abhay.weather.domain.weather.CurrentWeatherDetails
import com.abhay.weather.domain.weather.WeatherInfo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


fun WeatherDto2.toWeatherInfo(): WeatherInfo {

    val currentHour = days[0].hours.find { hour ->
        val now = LocalTime.now()
        val nearestHour = now.truncatedTo(ChronoUnit.HOURS)
        val formatted = nearestHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        formatted == hour.datetime
    }
    val temp = currentConditions.temp
    val weatherDesc = currentConditions.conditions
    val feelsLike = currentConditions.feelslike
    val pressure = currentConditions.pressure
    val humidity = currentConditions.humidity
    val windSpeed = currentConditions.windspeed

    val currentWeatherSummary = days[0].description
    val currentHourlyForecast = days[0].hours
    val tempMax = days[0].feelslikemax
    val tempMin = days[0].feelslikemin
    val visibility = currentConditions.visibility
    val listOfDays = days
    val uvIndex = currentConditions.uvindex
    val precip = currentConditions.precipprob

    val sunriseT = currentConditions.sunrise
    val sunsetT = currentConditions.sunset

    val sunrise = LocalTime
        .parse(sunriseT, DateTimeFormatter.ofPattern("HH:mm:ss"))
        .format(DateTimeFormatter.ofPattern("h:mm a"))

    val sunset = LocalTime
        .parse(sunsetT, DateTimeFormatter.ofPattern("HH:mm:ss"))
        .format(DateTimeFormatter.ofPattern("h:mm a"))

    val date = LocalDate.now().toString()
    val formattedDate =
        LocalDate.parse(date).format(DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH))

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
        visibility = visibility,
        uvIndex = uvIndex,
        precipProb = precip
    )
    return WeatherInfo(
        currentWeatherData = currentWeather,
        weatherForecastDetails = listOfDays,
        currentHourlyForecast = currentHourlyForecast,
        locationName = ""
    )
}

fun WeatherDataWithDays.toWeatherInfo(): WeatherInfo {
    val locationName = weatherData.locationName
    val daysList = days
    val date = LocalDate.now().toString()
    val formattedDate =
        LocalDate.parse(date).format(DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH))


    val currentWeatherDetails = CurrentWeatherDetails(
        dateAndDay = formattedDate,
        weatherDesc = weatherData.weatherDesc,
        temp = weatherData.temp,
        tempMax = days[0].feelslikemax,
        tempMin = days[0].feelslikemin,
        feelsLike = weatherData.feelsLike,
        visibility = weatherData.visibility,
        pressure = weatherData.pressure,
        humidity = weatherData.humidity,
        windSpeed = weatherData.windSpeed,
        sunrise = weatherData.sunrise,
        sunset = weatherData.sunset,
        currentWeatherSummary = days[0].description,
        uvIndex = weatherData.uvIndex,
        precipProb = weatherData.precipProb

    )

    return WeatherInfo(
        currentWeatherData = currentWeatherDetails,
        weatherForecastDetails = daysList.map { it.toDay() },
        currentHourlyForecast = emptyList(),
        locationName = locationName
    )
}

fun Days.toDay(): Day {

    return Day(
        conditions = conditions,
        datetime = datetime,
        description = description,
        feelslike = temp,
        feelslikemax = feelslikemax,
        feelslikemin = feelslikemin,
        hours = emptyList(),
        humidity = humidity,
        precip = 0.0,
        precipprob = 0.0,
        pressure = pressure,
        sunrise = sunrise,
        sunset = sunset,
        temp = temp,
        uvindex = 0,
        visibility = visibility,
        windspeed = windspeed
    )
}

fun Day.toDays(id: Int): Days {

    val currentHour = hours.find { hour ->
        val now = LocalTime.now()
        val nearestHour = now.truncatedTo(ChronoUnit.HOURS)
        val formatted = nearestHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        formatted == hour.datetime
    }

    return Days(
        dayId= id,
        weatherId = 0,
        conditions = currentHour!!.conditions,
        datetime = datetime,
        description = description,
        feelslikemax = feelslikemax,
        feelslikemin = feelslikemin,
        humidity = humidity,
        pressure = pressure,
        temp = temp,
        visibility = visibility,
        windspeed = windspeed,
        sunrise = sunrise,
        sunset = sunset
    )
}